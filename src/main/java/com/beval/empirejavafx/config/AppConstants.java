package com.beval.empirejavafx.config;

public class AppConstants {
    private AppConstants() {}
    public static final String API_URL = "http://localhost:8084/api/v1";
    public static final String LOGIN_URL = API_URL + "/auth/signin";
    public static final String REGISTER_URL = API_URL + "/auth/signup";
    public static final String LOAD_USER_CASTLE_URL = API_URL + "/castle/load";
    public static final String UPDATE_USER_STATE_URL = API_URL + "/user/info";
    public static final String UPGRADE_BUILDING_URL = API_URL + "/building/upgrade";
    public static final String CREATE_BUILDING_URL = API_URL + "/building/create";

    public static final String START_ANIMATION_URL =
            "https://empire-html5.goodgamestudios.com/default/ModernStartscreenAnimation_small.mp4";
}
