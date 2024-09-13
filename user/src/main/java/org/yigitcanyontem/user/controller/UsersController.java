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
import org.yigitcanyontem.clients.users.UsersClient;
import org.yigitcanyontem.user.domain.Users;
import org.yigitcanyontem.user.security.UsersPrincipal;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UsersController {
    private final UsersClient usersClient;

    @GetMapping("/hello")
    @PreAuthorize("hasRole('USER')")
    public String testUserRole(){
        return getLoggedInUser().getUsername();
    }

    private UsersPrincipal getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            return (UsersPrincipal) authentication.getPrincipal();
        }
        throw new UsernameNotFoundException("User not found..!!");
    }
}
