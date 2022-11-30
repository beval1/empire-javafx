package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.dto.response.UserInfoDTO;

import java.io.IOException;

public class UserStateManager {
    private UserStateManager() {}
    private static String username;
    private static int level;
    private static int mightyPoints;
    private static int coins;
    private static int rubies;

    public static String getUsername() {
        return username;
    }

    public static int getLevel() {
        return level;
    }

    public static int getMightyPoints() {
        return mightyPoints;
    }

    public static int getCoins() {
        return coins;
    }

    public static int getRubies() {
        return rubies;
    }

    public static void updateUserState() throws IOException, InterruptedException {
        ResponseDTO<UserInfoDTO> responseDTO = ApiClient.fetchUserInfo();
        UserStateManager.username = responseDTO.getContent().getUsername();
        UserStateManager.level = responseDTO.getContent().getLevel();
        UserStateManager.mightyPoints = responseDTO.getContent().getMightyPoints();
        UserStateManager.coins = responseDTO.getContent().getCoins();
        UserStateManager.rubies = responseDTO.getContent().getRubies();
    }

}
