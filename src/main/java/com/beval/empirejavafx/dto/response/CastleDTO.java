package com.beval.empirejavafx.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CastleDTO {
    private String castleName;
    private List<CastleBuildingDTO> buildings;
    private int coordinateX;
    private int coordinateY;
    private double wood;
    private double stone;
    private double food;
    private int armySize;
    private int citizens;
}
