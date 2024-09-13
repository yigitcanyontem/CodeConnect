package org.yigitcanyontem.clients.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.yigitcanyontem.clients.FeignConfiguration;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@FeignClient(name = "auth", configuration = FeignConfiguration.class)
public interface AuthClient {
    @PostMapping("/validate-token")
    ResponseEntity<UsersDto> validateToken(@RequestBody String token);
}
