package com.beval.empirejavafx.api;

import com.beval.empirejavafx.config.ApiConfig;
import com.beval.empirejavafx.dto.payload.CreateBuildingDTO;
import com.beval.empirejavafx.dto.payload.SignInDTO;
import com.beval.empirejavafx.dto.payload.SignUpDTO;
import com.beval.empirejavafx.dto.response.*;
import com.beval.empirejavafx.utils.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiClient {
    private ApiClient() {}
    public static final HttpClient client = HttpClient.newHttpClient();
    private static String bearerToken = "";

    public static ResponseDTO<JwtResponseDTO> signIn(String usernameOrEmail, String password) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.LOGIN_URL))
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.getMapper()
                        .writeValueAsString(new SignInDTO(usernameOrEmail, password))))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        ResponseDTO<JwtResponseDTO> responseDTO = JsonMapper.getMapper().readValue(httpResponse.body(),
                new TypeReference<>() {});
        if (httpResponse.statusCode() == 200) {
            bearerToken = responseDTO.getContent().getAccessToken();
        }
        return responseDTO;
    }

    public static ResponseDTO<Object> signUp(String username, String email, String password) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.REGISTER_URL))
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.getMapper()
                        .writeValueAsString(new SignUpDTO(username, email, password, "", ""))))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }

    public static ResponseDTO<CastleDTO> loadUserCastle(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.LOAD_USER_CASTLE_URL + String.format("?username=%s", username)))
                .header("Authorization", String.format("Bearer %s", bearerToken))
                .GET()
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }

    public static ResponseDTO<Object> createBuilding(int row, int column, int typeId) throws IOException, InterruptedException {
        CreateBuildingDTO createBuildingDTO = new CreateBuildingDTO(row, column, typeId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.CREATE_BUILDING_URL))
                .header("Authorization", String.format("Bearer %s", bearerToken))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.getMapper()
                        .writeValueAsString(createBuildingDTO)))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }
//
//    public static ResponseDTO upgradeBuilding(int buildingType, int castleBuildingId) throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(ApiConfig.UPGRADE_BUILDING_URL))
//                .GET()
//                .build();
//        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return JsonMapper.getMapper().readValue(httpResponse.body(), ResponseDTO.class);
//    }

    public static ResponseDTO<UserInfoDTO> fetchUserInfo() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.UPDATE_USER_STATE_URL))
                .header("Authorization", String.format("Bearer %s", bearerToken))
                .GET()
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }

    public static ResponseDTO<List<BuildingEntityDTO>> getBuildings() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.GET_BUILDINGS_URL))
                .header("Authorization", String.format("Bearer %s", bearerToken))
                .GET()
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }
}
