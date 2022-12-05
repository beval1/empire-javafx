package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.config.AppConstants;
import com.beval.empirejavafx.dto.response.*;
import com.beval.empirejavafx.exception.CustomException;
import com.beval.empirejavafx.utils.GridUtils;
import com.beval.empirejavafx.views.buildingpropertymenu.BuildingPropertyMenu;
import com.beval.empirejavafx.views.castlemenu.CastleMenu;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
    private static int castleQuadrant;
    private static double wood;
    private static double stone;
    private static double food;
    private static int army;
    private static int citizens;

    public static int getCastleQuadrant() {
        return castleQuadrant;
    }

    public static void setCastleQuadrant(int castleQuadrant) {
        CastleStateManager.castleQuadrant = castleQuadrant;
    }

    public static MapCastleDTO getSelectedEnemyCastle() {
        return selectedEnemyCastle;
    }

    public static void setSelectedEnemyCastle(MapCastleDTO selectedEnemyCastle) {
        CastleStateManager.selectedEnemyCastle = selectedEnemyCastle;
    }

    private static MapCastleDTO selectedEnemyCastle;

    public static double getWood() {
        return wood;
    }

    public static void setWood(double wood) {
        CastleStateManager.wood = wood;
    }

    public static double getStone() {
        return stone;
    }

    public static void setStone(double stone) {
        CastleStateManager.stone = stone;
    }

    public static double getFood() {
        return food;
    }

    public static void setFood(double food) {
        CastleStateManager.food = food;
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
        setArmy(castleDTO.getArmySize());
        setCitizens(castleDTO.getCitizens());
        setCastleQuadrant(castleDTO.getQuadrant());

        //set Image on Grid row and column
        for (CastleBuildingDTO building : buildings) {
            visualizeGridBuilding(gridPane, building, true);
        }
    }

    private static void visualizeGridBuilding(GridPane gridPane, CastleBuildingDTO building, boolean clickable) {
        Node node = GridUtils.getNodeFromGridPane(building.getCoordinateY(),
                building.getCoordinateX(), gridPane);
        if (node instanceof ImageView && ((ImageView) node).getImage().getUrl().equals(building.getBuildingEntity().getBuildingImage())){
            //don't redraw if it's already there
            return;
        }

        ImageView imageView = new ImageView(new Image(building.getBuildingEntity().getBuildingImage()));
        imageView.setFitHeight(AppConstants.CASTLE_BUILDING_IMAGE_HEIGHT * building
                .getBuildingEntity().getBuildingType().getHeightSizingRatio());
        imageView.setFitWidth(AppConstants.CASTLE_BUILDING_IMAGE_WIDTH * building
                .getBuildingEntity().getBuildingType().getWidthSizingRatio());
        if (clickable) {
            imageView.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    System.out.println("image clicked");
                    BuildingStateManager.setSelectedCastleBuilding(building);
                    BuildingPropertyMenu buildingPropertyMenu = new BuildingPropertyMenu();
                    try {
                        buildingPropertyMenu.show();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        int rowSpan = (int) Math.ceil(building.getBuildingEntity().getBuildingType().getHeightSizingRatio());
        int colSpan = (int) Math.ceil(building.getBuildingEntity().getBuildingType().getWidthSizingRatio());
        //first remove Pane element, then add ImageView
        GridUtils.removeNodeByRowColumnIndex(building.getCoordinateY(), building.getCoordinateX(),
                gridPane);
        gridPane.add(imageView, building.getCoordinateX(), building.getCoordinateY(), rowSpan, colSpan);
    }

    public static void loadMapCastles(int quadrant, GridPane gridPane) throws IOException, InterruptedException {
        ResponseDTO<List<MapCastleDTO>> responseDTO = ApiClient.loadMapQuadrant(quadrant);
        if (responseDTO.getStatus() != 200){
            throw new CustomException(responseDTO.getMessage());
        }

        List<MapCastleDTO> mapCastleDTOS = responseDTO.getContent();
        for (MapCastleDTO mapCastleDTO : mapCastleDTOS) {
            Node node = GridUtils.getNodeFromGridPane(mapCastleDTO.getCoordinateY(),
                    mapCastleDTO.getCoordinateX(), gridPane);
            if (node instanceof ImageView && ((ImageView) node).getImage().getUrl().equals(mapCastleDTO.getCastleImage())){
                //don't redraw if it's already there
                continue;
            }

            ImageView imageView = new ImageView(new Image(mapCastleDTO.getCastleImage()));
            imageView.setFitHeight(AppConstants.MAP_CASTLE_IMAGE_HEIGHT);
            imageView.setFitWidth(AppConstants.MAP_CASTLE_IMAGE_WIDTH);
            boolean isOwnCastle = CastleStateManager.getCastleWorldMapCoordinateX() != mapCastleDTO.getCoordinateX() ||
                    CastleStateManager.getCastleWorldMapCoordinateY() != mapCastleDTO.getCoordinateY();
            if (isOwnCastle) {
                imageView.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                        System.out.println("here");
                        CastleStateManager.setSelectedEnemyCastle(mapCastleDTO);
                        CastleMenu castleMenu = new CastleMenu();
                        try {
                            castleMenu.show();
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            //first remove Pane element, then add ImageView
            GridUtils.removeNodeByRowColumnIndex(mapCastleDTO.getCoordinateY(), mapCastleDTO.getCoordinateX(),
                    gridPane);
            gridPane.add(imageView, mapCastleDTO.getCoordinateX(), mapCastleDTO.getCoordinateY());
        }
    }

    public static void loadEnemyCastle(GridPane gridPane) throws IOException, InterruptedException {
        ResponseDTO<EnemyCastleDTO> responseDTO = ApiClient.loadEnemyCastle(
                CastleStateManager.getSelectedEnemyCastle().getOwnerUsername());
        if (responseDTO.getStatus() != 200){
            throw new CustomException(responseDTO.getMessage());
        }

        EnemyCastleDTO enemyCastleDTO = responseDTO.getContent();
        for (CastleBuildingDTO building : enemyCastleDTO.getBuildings()) {
            CastleStateManager.visualizeGridBuilding(gridPane, building, false);
        }
    }
}
