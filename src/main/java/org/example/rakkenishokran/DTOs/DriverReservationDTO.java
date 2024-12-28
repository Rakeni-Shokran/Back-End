package org.example.rakkenishokran.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

// RESERVATION.id, PARKING_LOT.id, location, name, pricingStructure, RESERVATION.startTimeStamp, RESERVATION.endTimeStamp

@Data
@Builder
@AllArgsConstructor
public class DriverReservationDTO {
    long reservationId;
    long parkingLotId;
    String location;
    String name;
    int pricingStructure;
    Timestamp startTimeStamp;
    Timestamp endTimeStamp;
    long parkingSpotId;
}
