package org.yigitcanyontem.cache.service;

import lombok.RequiredArgsConstructor;
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
public class CacheService{
    private final UsersHashRepository usersHashRepository;

    // Fetching users with caching
    public UsersDto getUserByEmail(String email) {
        Optional<UsersHash> usersHash = usersHashRepository.findByEmail(email);

        return usersHash.map(hash -> new UsersDto(
                hash.getId(),
                hash.getUsername(),
                null,
                hash.getEmail(),
                Role.valueOf(hash.getRole()),
                hash.isEnabled(),
                hash.getCreatedAt()
        )).orElse(null);
    }

    // Adding or updating a user and updating cache
    public void saveOrUpdateUser(UsersDto user) {
        usersHashRepository.save(new UsersHash(
                user.getId(),
                user.getUsername(),
                null,
                user.getEmail(),
                user.getRole().toString(),
                user.isEnabled(),
                user.getCreatedAt()
        ));
    }

    // Deleting user from the cache and repository
    public void deleteUserByEmail(String email) {
        usersHashRepository.deleteByEmail(email);
    }
}
