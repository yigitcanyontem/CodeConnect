package org.yigitcanyontem.clients.users;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@FeignClient(name = "users")
public interface UsersClient {
    @GetMapping(path = "api/v1/user/hello")
    UsersDto testUserRole(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader);

    @GetMapping(path = "api/v1/user/username/{username}")
    UsersDto getUsersByUsername(@PathVariable("username") String username);

    @GetMapping(path = "api/v1/user/email/{email}")
    UsersDto getUserByEmail(@PathVariable("email") String email);

    @PostMapping(path = "api/v1/user")
    UsersDto save(@RequestBody UsersDto user);
}
