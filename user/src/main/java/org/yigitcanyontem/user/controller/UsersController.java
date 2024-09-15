package org.yigitcanyontem.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.auth.AuthClient;
import org.yigitcanyontem.clients.users.dto.UserRegisterDTO;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.user.service.UsersService;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("username/{username}")
    public UsersDto getUsersByUsername(@PathVariable("username") String username) {
        return usersService.getUsersByUsername(username);
    }

    @GetMapping("email/{email}")
    public UsersDto getUserByEmail(@PathVariable("email") String email) {
        return usersService.getUserByEmail(email);
    }

    @PostMapping()
    public UsersDto save(@RequestBody UsersDto user) {
         return usersService.save(user);
    }

    @PostMapping("exists")
    public boolean userExists(@RequestBody UserRegisterDTO user) {
        return usersService.userExists(user);
    }

}
