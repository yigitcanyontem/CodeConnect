package org.yigitcanyontem.clients.users;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "users")
public interface UsersClient {
    @GetMapping(path = "api/v1/user/hello")
    String testUserRole();

}
