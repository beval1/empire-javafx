package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.config.AppConstants;
import com.beval.empirejavafx.dto.response.CastleBuildingDTO;
import com.beval.empirejavafx.dto.response.CastleDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CastleStateManager {
    private CastleStateManager() {}
    private static String castleName;
    private static List<CastleBuildingDTO> buildings = new ArrayList<>();
    private static int castleWorldMapCoordinateX;
    private static int castleWorldMapCoordinateY;
    private static int wood;
    private static int stone;
    private static int food;
    private static int army;
    private static int citizens;

    public static int getWood() {
        return wood;
    }

    public static void setWood(int wood) {
        CastleStateManager.wood = wood;
    }

    public static int getStone() {
        return stone;
    }

    public static void setStone(int stone) {
        CastleStateManager.stone = stone;
    }

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

    public static String getCastleName() {
        return castleName;
    }

    public static void setCastleName(String castleName) {
        CastleStateManager.castleName = castleName;
    }

    public static int getFood() {
        return food;
    }

    public static void setFood(int food) {
        CastleStateManager.food = food;
    }

    public static int getArmy() {
        return army;
    }

    public static void setArmy(int army) {
        CastleStateManager.army = army;
    }

    public static int getCitizens() {
        return citizens;
    }

    public static void setCitizens(int citizens) {
        CastleStateManager.citizens = citizens;
    }

    public static void loadUserCastle(String username, GridPane gridPane) throws IOException, InterruptedException {
        //send request to fetch castle
        ResponseDTO<CastleDTO> responseDTO = ApiClient.loadUserCastle(username);
        if (responseDTO.getStatus() != 200){
            throw new IllegalStateException("Can not load castle");
        }
        //update state
        CastleDTO castleDTO = responseDTO.getContent();
        List<CastleBuildingDTO> buildings = castleDTO.getBuildings();
        setBuildings(buildings);
        setCastleName(castleDTO.getCastleName());
        setCastleWorldMapCoordinateX(castleDTO.getCoordinateX());
        setCastleWorldMapCoordinateY(castleDTO.getCoordinateY());
        setWood(castleDTO.getWood());
        setStone(castleDTO.getStone());
        setFood(castleDTO.getFood());
        setArmy(castleDTO.getArmy());
        setCitizens(castleDTO.getCitizens());

        //set Image on Grid row and column
        for (CastleBuildingDTO building : buildings) {
//            Pane pane = (Pane) getNodeFromGridPane(gridPane, building.getCoordinateY(), building.getCoordinateX());
            ImageView imageView = new ImageView(new Image(building.getBuildingEntity().getBuildingImage()));
            imageView.setFitHeight(AppConstants.CASTLE_BUILDING_IMAGE_HEIGHT * building
                    .getBuildingEntity().getBuildingType().getHeightSizingRatio());
            imageView.setFitWidth(AppConstants.CASTLE_BUILDING_IMAGE_WIDTH * building
                    .getBuildingEntity().getBuildingType().getWidthSizingRatio());
            gridPane.add(imageView, building.getCoordinateX(), building.getCoordinateY());
        }
    }

    private static Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
