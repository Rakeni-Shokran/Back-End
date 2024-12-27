package org.example.rakkenishokran.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveReservation(long parkingSpotId, long driverId, String startTime, String endTime, long totalCost) {
        jdbcTemplate.update("INSERT INTO RESERVATION (parkingSpotId, userId, startTimeStamp, endTimeStamp, price) VALUES (?, ?, ?, ?, ?)",
                parkingSpotId, driverId, startTime, endTime, totalCost
        );
    }
}
