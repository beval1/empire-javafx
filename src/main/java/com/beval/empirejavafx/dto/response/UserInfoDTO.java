package com.beval.empirejavafx.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private String username;
    private int level;
    private int mightyPoints;
    private int coins;
    private int rubies;
}
