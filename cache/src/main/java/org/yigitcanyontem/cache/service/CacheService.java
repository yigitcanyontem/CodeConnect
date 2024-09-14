package org.yigitcanyontem.cache.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.cache.hash.UsersHash;
import org.yigitcanyontem.cache.repository.UsersHashRepository;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.clients.users.enums.Role;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheService{
    private final UsersHashRepository usersHashRepository;

    // Fetching users with caching
    public UsersDto getUserByEmail(String email) {
        Optional<UsersHash> usersHash = usersHashRepository.findByEmail(email);

        return usersHash.map(hash -> new UsersDto(
                hash.getId(),
                hash.getUsername(),
                hash.getEmail(),
                null,
                Role.valueOf(hash.getRole()),
                hash.isEnabled(),
                hash.getCreatedAt()
        )).orElse(null);
    }

    // Adding or updating a user and updating cache
    public void saveOrUpdateUser(UsersDto user) {
        UsersHash usersHash = usersHashRepository.save(new UsersHash(
                user.getId(),
                user.getUsername(),
                null,
                user.getEmail(),
                user.getRole().toString(),
                user.isEnabled(),
                user.getCreatedAt()
        ));
        log.info("User saved with id: {}", usersHash.getId());
    }

    // Deleting user from the cache and repository
    public void deleteUserByEmail(String email) {
        usersHashRepository.deleteByEmail(email);
        log.info("User deleted with email: {}", email);
    }
}
