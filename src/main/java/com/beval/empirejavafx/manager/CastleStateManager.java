package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.alerts.SoldiersDesertingAlert;
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
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CastleStateManager {
    private CastleStateManager() {}
    @Getter
    @Setter
    private static String castleName;
    @Getter
    @Setter
    private static List<CastleBuildingDTO> buildings = new ArrayList<>();
    @Getter
    @Setter
    private static int castleWorldMapCoordinateX;
    @Getter
    @Setter
    private static int castleWorldMapCoordinateY;
    @Getter
    @Setter
    private static int castleQuadrant;
    @Getter
    @Setter
    private static double wood;
    @Getter
    @Setter
    private static double stone;
    @Getter
    @Setter
    private static double food;
    @Getter
    @Setter
    private static double woodProduction;
    @Getter
    @Setter
    private static double stoneProduction;
    @Getter
    @Setter
    private static double foodProduction;
    @Getter
    @Setter
    private static int army;
    @Getter
    @Setter
    private static int citizens;
    @Getter
    @Setter
    private static MapCastleDTO selectedEnemyCastle;
    @Getter
    @Setter
    private static boolean soldierDesertingAlertShown = false;

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
        setWoodProduction(castleDTO.getWoodProduction());
        setStoneProduction(castleDTO.getStoneProduction());
        setFoodProduction(castleDTO.getFoodProduction());
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

        if (getFood() == 0 && foodProduction < 0 && !soldierDesertingAlertShown){
            SoldiersDesertingAlert soldiersDesertingAlert = new SoldiersDesertingAlert();
            soldiersDesertingAlert.show();
            soldierDesertingAlertShown = true;
        }

        if (getFood() > 0 && foodProduction > 0){
            soldierDesertingAlertShown = false;
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
