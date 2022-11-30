package com.beval.empirejavafx.dto.payload;

import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBuildingDTO {
    private int row;
    private int column;
    private int typeId;
}
