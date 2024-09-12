package org.yigitcanyontem.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.user.domain.Users;
import org.yigitcanyontem.user.dto.UserRegisterDTO;
import org.yigitcanyontem.user.enums.Role;
import org.yigitcanyontem.user.repository.UsersRepository;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users register(UserRegisterDTO userRegisterDTO) {
        Users user = new Users(userRegisterDTO);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setEnabled(true);
        return usersRepository.save(user);
    }

    public Users getUsersByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public Users save(Users user) {
        return usersRepository.save(user);
    }
}
