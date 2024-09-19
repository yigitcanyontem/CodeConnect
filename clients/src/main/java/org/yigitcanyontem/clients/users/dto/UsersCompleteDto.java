package org.yigitcanyontem.clients.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yigitcanyontem.clients.users.profile.UsersProfileDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersCompleteDto {
    private UsersDto user;
    private UsersProfileDto profile;
}
