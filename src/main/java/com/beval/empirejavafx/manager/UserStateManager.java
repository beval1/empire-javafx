package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.PlayerMessageDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.dto.response.UserInfoDTO;
import com.beval.empirejavafx.exception.CustomException;

import java.io.IOException;
import java.util.List;

public class UserStateManager {
    private UserStateManager() {}
    private static String username;
    private static int level;
    private static int mightyPoints;
    private static int coins;
    private static int rubies;
    private static List<PlayerMessageDTO> messages;

    public static List<PlayerMessageDTO> getMessages() {
        return messages;
    }

    public static void setMessages(List<PlayerMessageDTO> messages) {
        UserStateManager.messages = messages;
    }

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

    public static void loadUserMessages() throws IOException, InterruptedException {
        ResponseDTO<List<PlayerMessageDTO>> responseDTO = ApiClient.loadUserMessages();
        if (responseDTO.getStatus() != 200){
            throw new CustomException(responseDTO.getMessage());
        }
        setMessages(responseDTO.getContent());
    }

}
