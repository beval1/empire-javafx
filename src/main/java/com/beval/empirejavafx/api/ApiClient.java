package com.beval.empirejavafx.api;

import com.beval.empirejavafx.config.ApiConfig;
import com.beval.empirejavafx.dto.payload.*;
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
        CreateBuildingDTO createBuildingDTO = CreateBuildingDTO.builder()
                .row(row)
                .column(column)
                .typeId(typeId)
                .build();
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

    public static ResponseDTO<List<BuildingEntityDTO>> getSpecificBuildingType(int buildingId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.GET_SPECIFIC_BUILDING_TYPE_URL + buildingId))
                .header("Authorization", String.format("Bearer %s", bearerToken))
                .GET()
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }

    public static ResponseDTO<Object> upgradeBuilding(int castleBuildingId) throws IOException, InterruptedException {
        UpgradeBuildingDTO upgradeBuildingDTO = UpgradeBuildingDTO
                .builder()
                .buildingId(castleBuildingId)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.UPGRADE_BUILDING_URL))
                .header("Authorization", String.format("Bearer %s", bearerToken))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.getMapper()
                        .writeValueAsString(upgradeBuildingDTO)))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }

    public static ResponseDTO<Object> destroyBuilding(int castleBuildingId) throws IOException, InterruptedException {
        DestroyBuildingDTO destroyBuildingDTO = DestroyBuildingDTO
                .builder()
                .buildingId(castleBuildingId)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ApiConfig.DESTROY_BUILDING_URL))
                .header("Authorization", String.format("Bearer %s", bearerToken))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.getMapper()
                        .writeValueAsString(destroyBuildingDTO)))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), new TypeReference<>() {});
    }

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
