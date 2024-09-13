package org.yigitcanyontem.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yigitcanyontem.clients.notification.NotificationClient;
import org.yigitcanyontem.clients.notification.NotificationCreateDto;
import org.yigitcanyontem.clients.users.UsersClient;
import org.yigitcanyontem.user.domain.Users;
import org.yigitcanyontem.user.security.UsersPrincipal;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UsersController {
    private final UsersClient usersClient;
    private final NotificationClient notificationClient;

    @GetMapping("/hello")
    @PreAuthorize("hasRole('USER')")
    public String testUserRole(){
        notificationClient.sendNotification(
                new NotificationCreateDto(
                        1,
                        "message"
                )
        );
        return "Hello User";
    }

    private UsersPrincipal getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return (UsersPrincipal) authentication.getPrincipal();
        }
        throw new UsernameNotFoundException("User not found..!!");
    }
}
