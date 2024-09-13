package org.yigitcanyontem.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.user.domain.Users;
import org.yigitcanyontem.user.repository.UsersRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersDto getUsersByUsername(String username) {
        Users user = usersRepository.findByUsername(username);
        return new UsersDto().builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UsersDto getUserByEmail(String email) {
        Users user = usersRepository.findByEmail(email);
        return new UsersDto().builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UsersDto save(UsersDto user) {
        Users newUser = Users.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .build();
         newUser = usersRepository.saveAndFlush(newUser);
         user.setId(newUser.getId());
         return user;
    }
}
