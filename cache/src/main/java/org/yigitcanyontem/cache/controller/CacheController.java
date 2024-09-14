package org.yigitcanyontem.cache.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.cache.service.CacheService;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@RestController
@RequestMapping("api/v1/cache")
@AllArgsConstructor
@Slf4j
public class CacheController {
    private final CacheService cacheService;

    @GetMapping("/users/{email}")
    public ResponseEntity<UsersDto> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(cacheService.getUserByEmail(email));
    }

    @PostMapping("/users")
    public ResponseEntity<Void> saveOrUpdateUser(@RequestBody UsersDto user, @RequestParam("email") String email){
        cacheService.saveOrUpdateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable("email") String email) {
        cacheService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
