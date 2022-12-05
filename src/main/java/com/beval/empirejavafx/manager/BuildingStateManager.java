package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.dto.response.BuildingEntityDTO;
import com.beval.empirejavafx.dto.response.CastleBuildingDTO;
import javafx.scene.Cursor;
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

}
