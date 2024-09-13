package org.yigitcanyontem.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.notification.NotificationClient;
import org.yigitcanyontem.clients.notification.NotificationCreateDto;
import org.yigitcanyontem.clients.users.UsersClient;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.user.service.UsersService;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    private final NotificationClient notificationClient;

    @GetMapping("/hello")
    public String testUserRole(){
        notificationClient.sendNotification(
                new NotificationCreateDto(
                        1,
                        "message"
                )
        );
        return "Hello User";
    }

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

}
