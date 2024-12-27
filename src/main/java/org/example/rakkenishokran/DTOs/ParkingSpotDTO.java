package org.example.rakkenishokran.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
public class ParkingSpotDTO {
    private long id;
    private long parkingLotId;
    private String status;
    private String type;
}
