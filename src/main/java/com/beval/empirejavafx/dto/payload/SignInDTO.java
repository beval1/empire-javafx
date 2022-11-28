package com.beval.empirejavafx.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInDTO {
    private String usernameOrEmail;
    private String password;
}
