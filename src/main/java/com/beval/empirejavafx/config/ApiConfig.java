package com.beval.empirejavafx.config;

public class ApiConfig {
    public static final String API_URL = "http://localhost:8084/api/v1";
    public static final String LOGIN_URL = API_URL + "/auth/signin";
    public static final String REGISTER_URL = API_URL + "/auth/signup";
    public static final String LOAD_USER_CASTLE_URL = API_URL + "/castle/load";
    public static final String UPDATE_USER_STATE_URL = API_URL + "/user/info";
    public static final String GET_BUILDINGS_URL = API_URL + "/buildings/get-all";
    public static final String UPGRADE_BUILDING_URL = API_URL + "/buildings/upgrade/";
    public static final String CREATE_BUILDING_URL = API_URL + "/buildings/create";
    public static final String DESTROY_BUILDING_URL = API_URL + "/buildings/destroy";
    public static final String GET_SPECIFIC_BUILDING_TYPE_URL = API_URL + "/buildings/get/";
    public static final String GET_ARMY_UNITS_URL = API_URL + "/army/get-all";
    public static final String GET_CASTLE_ARMY_UNITS = API_URL + "/army/get-castle";
    public static final String BUY_ARMY_UNITS_URL = API_URL + "/army/buy";
    public static final String LOAD_MAP_QUADRANT = API_URL + "/map?quadrant=";
    public static final String LOAD_ENEMY_CASTLE = API_URL + "/castle/enemy/";
    public static final String SEND_MESSAGE = API_URL + "/message/send/";
    public static final String LOAD_USER_MESSAGES = API_URL + "/user/messages";

    private ApiConfig() {}
}
