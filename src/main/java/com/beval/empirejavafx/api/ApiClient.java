package com.beval.empirejavafx.api;

import com.beval.empirejavafx.config.AppConstants;
import com.beval.empirejavafx.dto.payload.SignInDTO;
import com.beval.empirejavafx.dto.payload.SignUpDTO;
import com.beval.empirejavafx.dto.response.JwtResponseDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.dto.response.UserInfoDTO;
import com.beval.empirejavafx.utils.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private ApiClient() {}
    public static final HttpClient client = HttpClient.newHttpClient();
    private static String bearerToken = "";

    public static ResponseDTO<JwtResponseDTO> signIn(String usernameOrEmail, String password) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppConstants.LOGIN_URL))
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.getMapper()
                        .writeValueAsString(new SignInDTO(usernameOrEmail, password))))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        ResponseDTO<JwtResponseDTO> responseDTO = JsonMapper.getMapper().readValue(httpResponse.body(),
                new TypeReference<>() {});
        bearerToken = responseDTO.getContent().getAccessToken();
        return responseDTO;
    }

    public static ResponseDTO<Object> signUp(String username, String email, String password) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppConstants.REGISTER_URL))
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.getMapper()
                        .writeValueAsString(new SignUpDTO(username, email, password, "", ""))))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }

//    public static ResponseDTO loadUserCastle(String username) throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(AppConstants.LOAD_USER_CASTLE_URL + String.format("?username=%s", username)))
//                .GET()
//                .build();
//        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return JsonMapper.getMapper().readValue(httpResponse.body(), ResponseDTO.class);
//    }
//
//    public static ResponseDTO createBuilding(int buildingType, int castleBuildingId) throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(AppConstants.CREATE_BUILDING_URL))
//                .GET()
//                .build();
//        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return JsonMapper.getMapper().readValue(httpResponse.body(), ResponseDTO.class);
//    }
//
//    public static ResponseDTO upgradeBuilding(int buildingType, int castleBuildingId) throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(AppConstants.UPGRADE_BUILDING_URL))
//                .GET()
//                .build();
//        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return JsonMapper.getMapper().readValue(httpResponse.body(), ResponseDTO.class);
//    }

    public static ResponseDTO<UserInfoDTO> fetchUserInfo() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppConstants.UPDATE_USER_STATE_URL))
                .header("Authorization", String.format("Bearer %s", bearerToken))
                .GET()
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }
}
