package org.yigitcanyontem.clients.users.dto;

import lombok.*;
import org.yigitcanyontem.clients.users.enums.Role;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDto {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private boolean enabled;
    private Date createdAt;

    public UsersDto(UserRegisterDTO userRegisterDTO) {
        this.username = userRegisterDTO.getUsername();
        this.email = userRegisterDTO.getEmail();
    }
}
