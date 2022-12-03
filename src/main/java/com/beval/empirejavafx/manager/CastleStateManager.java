package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.config.AppConstants;
import com.beval.empirejavafx.dto.response.CastleBuildingDTO;
import com.beval.empirejavafx.dto.response.CastleDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.views.buildingpropertymenu.BuildingPropertyMenu;
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
    private static double wood;
    private static double stone;
    private static double food;
    private static int army;
    private static int citizens;

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

        //set Image on Grid row and column
        for (CastleBuildingDTO building : buildings) {
            Node node = BuildingStateManager.getNodeFromGridPane(building.getCoordinateY(),
                    building.getCoordinateX(), gridPane);
            if (node instanceof ImageView && ((ImageView) node).getImage().getUrl().equals(building.getBuildingEntity().getBuildingImage())){
                //don't redraw if it's already there
                continue;
            }

            ImageView imageView = new ImageView(new Image(building.getBuildingEntity().getBuildingImage()));
            imageView.setFitHeight(AppConstants.CASTLE_BUILDING_IMAGE_HEIGHT * building
                    .getBuildingEntity().getBuildingType().getHeightSizingRatio());
            imageView.setFitWidth(AppConstants.CASTLE_BUILDING_IMAGE_WIDTH * building
                    .getBuildingEntity().getBuildingType().getWidthSizingRatio());
            imageView.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    System.out.println("image clicked");
                    BuildingStateManager.setSelectedCastleBuilding(building);
                    BuildingPropertyMenu buildingPropertyMenu = new BuildingPropertyMenu();
                    try {
                        buildingPropertyMenu.show();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
//                        Thread.currentThread().interrupt();
                    }
                }
            });
            int rowSpan = (int) Math.ceil(building.getBuildingEntity().getBuildingType().getHeightSizingRatio());
            int colSpan = (int) Math.ceil(building.getBuildingEntity().getBuildingType().getWidthSizingRatio());
            BuildingStateManager.removeNodeByRowColumnIndex(building.getCoordinateY(), building.getCoordinateX(),
                    gridPane);
            gridPane.add(imageView, building.getCoordinateX(), building.getCoordinateY(), rowSpan, colSpan);
        }
    }

}
