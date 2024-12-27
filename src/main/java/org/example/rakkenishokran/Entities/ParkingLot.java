package org.example.rakkenishokran.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ParkingLot {
    private long id;
    private String name;
    private String location;
    private int capacity;
    private int pricingStructure;
    private long parkingLotManagerId;
}
