package org.yigitcanyontem.clients.users;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.yigitcanyontem.clients.FeignConfiguration;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@FeignClient(name = "users", configuration = FeignConfiguration.class)
public interface UsersClient {
    @GetMapping(path = "api/v1/user/hello")
    String testUserRole();

    @GetMapping(path = "api/v1/user/username/{username}")
    UsersDto getUsersByUsername(@PathVariable("username") String username);

    @GetMapping(path = "api/v1/user/email/{email}")
    UsersDto getUserByEmail(@PathVariable("email") String email);

    @PostMapping(path = "api/v1/user")
    UsersDto save(@RequestBody UsersDto user);
}
