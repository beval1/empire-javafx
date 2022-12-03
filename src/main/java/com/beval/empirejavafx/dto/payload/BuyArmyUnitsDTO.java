package com.beval.empirejavafx.dto.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyArmyUnitsDTO {
    private int armyUnitId;
    private int count;
}

