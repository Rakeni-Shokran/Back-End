package org.example.rakkenishokran.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reservation{
    private long id;
    private Timestamp startTimeStamp;
    private Timestamp endTimeStamp;
    private int price;
    private long userId;
    private long parkingSpotId;
    private boolean isReminded;
}
