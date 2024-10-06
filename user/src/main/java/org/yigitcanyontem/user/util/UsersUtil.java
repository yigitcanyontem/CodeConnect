package org.yigitcanyontem.user.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.clients.auth.AuthClient;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@Service
@RequiredArgsConstructor
public class UsersUtil {
    private final AuthClient authClient;

    public UsersDto throwIfJwtTokenIsInvalidElseReturnUser(String jwtToken) {
        UsersDto user = authClient.validateToken(jwtToken).getBody();

        if (user == null) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        return user;
    }
}
