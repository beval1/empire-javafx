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
}
