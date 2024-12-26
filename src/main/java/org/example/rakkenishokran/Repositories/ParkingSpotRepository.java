package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.ParkingSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ParkingSpotRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(ParkingSpot parkingSpot) {
        jdbcTemplate.update("INSERT INTO PARKING_SPOT (parkingLotId, status, type) VALUES (?, ?, ?)",
                parkingSpot.getParkingLotId(),
                parkingSpot.getStatus(),
                parkingSpot.getType()
        );
    }
}
