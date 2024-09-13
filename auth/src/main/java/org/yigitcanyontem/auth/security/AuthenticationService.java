package org.yigitcanyontem.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.yigitcanyontem.auth.domain.Token;
import org.yigitcanyontem.auth.repository.TokenRepository;
import org.yigitcanyontem.clients.users.UsersClient;
import org.yigitcanyontem.clients.users.dto.AuthenticationRequest;
import org.yigitcanyontem.clients.users.dto.AuthenticationResponse;
import org.yigitcanyontem.clients.users.dto.UserRegisterDTO;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.clients.users.enums.Role;
import org.yigitcanyontem.clients.users.enums.TokenType;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersClient usersClient;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UsersDetailsService userDetailsService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws LoginException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UsersDto user = usersClient.getUserByEmail(request.getUsername());
        if (!user.isEnabled()){
            throw new LoginException("Account doesn't exist");
        }

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .id(usersClient.getUserByEmail(request.getUsername()).getId())
                .build();
    }

    private void saveUserToken(UsersDto user, String jwtToken) {
        var token = Token.builder()
                .userId(user.getId())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UsersDto user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse register(UserRegisterDTO request) {
        var user = UsersDto.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();
        var savedUser = usersClient.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .id(usersClient.getUsersByUsername(request.getUsername()).getId())
                .build();
    }

    private UsersPrincipal getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return (UsersPrincipal) authentication.getPrincipal();
        }
        throw new UsernameNotFoundException("User not found..!!");
    }

    public UsersDto validateToken(String token) {
        token = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        UsersPrincipal userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtService.validateToken(token, userDetails)) {
            return userDetails.user();
        } else {
            return null;
        }
    }

}
