package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.dto.response.BuildingEntityDTO;
import com.beval.empirejavafx.dto.response.CastleBuildingDTO;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

public class BuildingStateManager {
    private BuildingStateManager() {
    }

    @Getter
    @Setter
    private static boolean inBuildingMode = false;
    @Getter
    @Setter
    private static BuildingEntityDTO buildingEntity = null;
    @Getter
    @Setter
    private static Cursor cursor = null;
    @Getter
    @Setter
    private static CastleBuildingDTO selectedCastleBuilding = null;
    @Getter
    @Setter
    private static GridPane buildingsGrid;

    public static Node getNodeFromGridPane(int row, int col, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null) {
                if (columnIndex == col && rowIndex == row){
                    return node;
                }
            }
        }
        return null;
    }

    public static void removeNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        gridPane.getChildren().removeIf(node -> {
            if (node != null) {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);
                if (columnIndex != null && rowIndex != null) {
                    return columnIndex == column && rowIndex == row;
                }
            }
            return false;
        });
    }
}
