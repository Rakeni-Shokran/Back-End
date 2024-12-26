package org.example.rakkenishokran.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class TypeOfSpotsInLot {
    private String typeOfSpots;
    private long parkingLotId;
}
