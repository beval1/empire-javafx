package com.beval.empirejavafx.manager;

import com.beval.empirejavafx.dto.response.BuildingEntityDTO;
import javafx.scene.Cursor;
import lombok.Getter;
import lombok.Setter;

public class BuildingStateManager {
    private BuildingStateManager() {}

    @Getter
    @Setter
    private static boolean inBuildingMode = false;
    @Getter
    @Setter
    private static BuildingEntityDTO buildingEntity = null;
    @Getter
    @Setter
    private static Cursor cursor = null;
}
