package org.yigitcanyontem.clients.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@FeignClient(name = "auth")
public interface AuthClient {
    @PostMapping("api/v1/auth/validate-token")
    ResponseEntity<UsersDto> validateToken(@RequestParam("token") String token);
}
