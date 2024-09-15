package org.yigitcanyontem.user.controller;

import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.auth.AuthClient;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.clients.users.profile.UsersProfileCreateDto;
import org.yigitcanyontem.clients.users.profile.UsersProfileDto;
import org.yigitcanyontem.clients.users.profile.UsersProfileUpdateDto;
import org.yigitcanyontem.user.domain.UsersProfile;
import org.yigitcanyontem.user.service.UsersProfileService;

@Slf4j
@RestController
@RequestMapping("api/v1/user-profile")
@RequiredArgsConstructor
public class UsersProfileController {
    private final UsersProfileService usersProfileService;
    private final AuthClient authClient;

    @GetMapping("current")
    public ResponseEntity<UsersProfileDto> getLoggedInUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);

            return new ResponseEntity<>(usersProfileService.getUsersProfileByUsersId(user.getId()), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Error while fetching user profile by user id: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<UsersProfileDto> getUserProfileByUserId(@PathVariable("userId") Integer userId) {
        try {
            return new ResponseEntity<>(usersProfileService.getUsersProfileByUsersId(userId), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Error while fetching user profile by user id: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("email/{email}")
    public ResponseEntity<UsersProfileDto> getUserProfileByEmail(@PathVariable("email") String email) {
        try {
            return new ResponseEntity<>(usersProfileService.getUsersProfileByEmail(email), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Error while fetching user profile by email: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<UsersProfileDto> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody UsersProfileCreateDto createDto) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);

            return new ResponseEntity<>(usersProfileService.save(createDto, user), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Error while saving user profile: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping()
    public ResponseEntity<UsersProfileDto> save(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody UsersProfileUpdateDto updateDto) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);

            return new ResponseEntity<>(usersProfileService.update(updateDto, user), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Error while updating user profile: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
            usersProfileService.delete(user);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("Error while deleting user profile: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public UsersDto throwIfJwtTokenIsInvalidElseReturnUser(String jwtToken) {
        UsersDto user = authClient.validateToken(jwtToken).getBody();

        if (user == null) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        return user;
    }

}