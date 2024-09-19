package org.yigitcanyontem.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.auth.AuthClient;
import org.yigitcanyontem.clients.users.dto.UserRegisterDTO;
import org.yigitcanyontem.clients.users.dto.UsersCompleteDto;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.user.service.UsersService;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("me")
    public ResponseEntity<UsersCompleteDto> getLoggedInUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
       try {
            return new ResponseEntity<>(usersService.getLoggedInUser(jwtToken), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching logged in user: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("username/{username}")
    public ResponseEntity<UsersDto> getUsersByUsername(@PathVariable("username") String username) {
        try {
            return new ResponseEntity<>(usersService.getUsersByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching user profile by username: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable("id") Integer id) {
        try {
            return new ResponseEntity<>(usersService.getUserById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching user profile by user id: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("email/{email}")
    public ResponseEntity<UsersDto> getUserByEmail(@PathVariable("email") String email) {
        try {
            return new ResponseEntity<>(usersService.getUserByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching user profile by email: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<UsersDto> save(@RequestBody UsersDto user) {
        try {
            return new ResponseEntity<>(usersService.save(user), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching user profile by user id: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("exists")
    public boolean userExists(@RequestBody UserRegisterDTO user) {
        return usersService.userExists(user);
    }

}
