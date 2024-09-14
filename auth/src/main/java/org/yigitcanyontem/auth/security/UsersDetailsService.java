package org.yigitcanyontem.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.clients.cache.CacheClient;
import org.yigitcanyontem.clients.users.UsersClient;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersDetailsService implements UserDetailsService {

    private final UsersClient usersClient;
    private final CacheClient cacheClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UsersPrincipal loadUserByUsername(String email) throws BadCredentialsException, AccessDeniedException {
        UsersDto users = objectMapper.convertValue(cacheClient.getValueFromCache("users-" + email), UsersDto.class);

        if (users == null) {
            users = usersClient.getUserByEmail(email);
        }

        if (users == null) {
            log.info("User not found with email: {}", email);
            return null;
        }

        cacheClient.putValueInCache("users-" + email, users);
        log.info("User logged in with email: {}", email);
        return new UsersPrincipal(users);
    }
}
