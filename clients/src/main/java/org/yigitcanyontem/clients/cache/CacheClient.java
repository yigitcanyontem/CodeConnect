package org.yigitcanyontem.clients.cache;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@FeignClient(name = "cache")
public interface CacheClient {
    @GetMapping("api/v1/cache/users/{email}")
    ResponseEntity<UsersDto> getUserByEmail(@PathVariable("email") String email);

    @PostMapping("api/v1/cache/users")
    ResponseEntity<UsersDto> saveOrUpdateUser(@RequestBody UsersDto user, @RequestParam("email") String email);

    @DeleteMapping("api/v1/cache/users/{email}")
    ResponseEntity<Void> deleteUserByEmail(@PathVariable("email") Integer id);

}


