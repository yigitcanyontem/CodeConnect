package org.yigitcanyontem.cache.hash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("users")
public class UsersHash {
    @Id
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean enabled;
    private LocalDateTime createdAt;
}
