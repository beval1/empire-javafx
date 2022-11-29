package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.CastleBuildingDTO;
import com.beval.empirejavafx.dto.response.CastleDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CastleStateManager {
    private static List<CastleBuildingDTO> buildings = new ArrayList<>();
    private CastleStateManager() {}
    private static int castleWorldMapCoordinateX;
    private static int castleWorldMapCoordinateY;

    public static void setBuildings(List<CastleBuildingDTO> buildings) {
        CastleStateManager.buildings = buildings;
    }

    public static List<CastleBuildingDTO> getBuildings() {
        return buildings;
    }

    public static int getCastleWorldMapCoordinateX() {
        return castleWorldMapCoordinateX;
    }

    public static void setCastleWorldMapCoordinateX(int castleWorldMapCoordinateX) {
        CastleStateManager.castleWorldMapCoordinateX = castleWorldMapCoordinateX;
    }

    public static int getCastleWorldMapCoordinateY() {
        return castleWorldMapCoordinateY;
    }

    public static void setCastleWorldMapCoordinateY(int castleWorldMapCoordinateY) {
        CastleStateManager.castleWorldMapCoordinateY = castleWorldMapCoordinateY;
    }

    public static void loadUserCastle(String username, GridPane gridPane) throws IOException, InterruptedException {
        //send request to fetch castle
        ResponseDTO<CastleDTO> responseDTO = ApiClient.loadUserCastle(username);
        if (responseDTO.getStatus() != 200){
            throw new IllegalStateException("Can not load castle");
        }
        //update state
        List<CastleBuildingDTO> buildings = responseDTO.getContent().getBuildings();
        setBuildings(buildings);
        setCastleWorldMapCoordinateX(responseDTO.getContent().getCoordinateX());
        setCastleWorldMapCoordinateY(responseDTO.getContent().getCoordinateY());
        //set Image on Grid row and column
        for (CastleBuildingDTO building : buildings) {
            ImageView imageView = new ImageView(new Image(building.getBuildingEntity().getBuildingImage()));
            imageView.setFitHeight(120);
            imageView.setFitWidth(120);
            gridPane.add(imageView, building.getCoordinateX(), building.getCoordinateY(), 4, 4);
        }
    }
}
