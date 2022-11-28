package com.beval.empirejavafx.dto.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SignUpDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
