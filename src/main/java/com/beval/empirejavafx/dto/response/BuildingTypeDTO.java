package com.beval.empirejavafx.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuildingTypeDTO {
    private String buildingName;
    private int castleLimit;
    private boolean buildable;
}
