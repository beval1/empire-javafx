package com.beval.empirejavafx.config;

public class ApiConfig {
    public static final String API_URL = "http://localhost:8084/api/v1";
    public static final String LOGIN_URL = API_URL + "/auth/signin";
    public static final String REGISTER_URL = API_URL + "/auth/signup";
    public static final String LOAD_USER_CASTLE_URL = API_URL + "/castle/load";
    public static final String UPDATE_USER_STATE_URL = API_URL + "/user/info";
    public static final String GET_BUILDINGS_URL = API_URL + "/buildings/get-all";
    public static final String UPGRADE_BUILDING_URL = API_URL + "/castle/upgrade-building";
    public static final String CREATE_BUILDING_URL = API_URL + "/castle/create-building";
    public static final String DESTROY_BUILDING_URL = API_URL + "/castle/destroy-building";
    public static final String GET_SPECIFIC_BUILDING_TYPE_URL = API_URL + "/buildings/get/";

    private ApiConfig() {}
}
