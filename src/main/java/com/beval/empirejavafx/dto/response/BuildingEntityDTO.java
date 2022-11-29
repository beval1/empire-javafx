package com.beval.empirejavafx.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuildingEntityDTO {
    private BuildingTypeDTO buildingType;
    private String buildingImage;
    private int castleLimit;
    private int level;
    private int woodRequired;
    private int stoneRequired;
    private int buildingTimeSeconds;
    private int unlocksOnLevel;
}
