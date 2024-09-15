package com.yigitcanyontem.validator.validators.user;

import org.yigitcanyontem.clients.users.dto.UserRegisterDTO;


public class UserValidator {

    public static void throwIfRegisterPayloadInvalid(UserRegisterDTO request) {
        if (!EmailValidator.isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (!PasswordValidator.isValidPassword(request.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
    }
}
