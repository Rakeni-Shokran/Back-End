package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.ParkingSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ParkingSpotRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ParkingSpot> findFreeParkingSpotsInTimeStampRange(String location, String startTime, String endTime){
        return jdbcTemplate.query(
                "SELECT PARKING_SPOT.id, status, type, parkingLotId FROM PARKING_LOT " +
                        "JOIN PARKING_SPOT ON PARKING_LOT.id = PARKING_SPOT.parkingLotId " +
                        "WHERE PARKING_LOT.location = ? " +
                        "AND " +
                        "(PARKING_SPOT.id NOT IN( " +
                        "SELECT DISTINCT PARKING_SPOT.id " +
                        "FROM PARKING_SPOT " +
                        "JOIN RESERVATION on PARKING_SPOT.id = RESERVATION.parkingSpotId " +
                        "WHERE " +
                        "(RESERVATION.startTimeStamp >= ? AND RESERVATION.startTimeStamp < ? AND RESERVATION.endTimeStamp >= ?) " +
                        "OR " +
                        "(RESERVATION.startTimeStamp < ? AND RESERVATION.endTimeStamp <= ? AND RESERVATION.endTimeStamp > ?) " +
                        "OR " +
                        "(RESERVATION.startTimeStamp <= ? AND RESERVATION.endTimeStamp >= ?) " +
                        "OR " +
                        "(RESERVATION.startTimeStamp >= ? AND RESERVATION.endTimeStamp <= ?)))",
                (rs, rowNum) -> new ParkingSpot(
                rs.getLong("id"),
                rs.getLong("parkingLotId"),
                rs.getString("status"),
                rs.getString("type")
        ), location, startTime, endTime, endTime, startTime, endTime, startTime, startTime, endTime, startTime, endTime);
    }

    public void save(ParkingSpot parkingSpot) {
        jdbcTemplate.update("INSERT INTO PARKING_SPOT (parkingLotId, status, type) VALUES (?, ?, ?)",
                parkingSpot.getParkingLotId(),
                parkingSpot.getStatus(),
                parkingSpot.getType()
        );
    }

    public long getLotBaseRate(long parkingSpotId) {
        return jdbcTemplate.queryForObject(
                "SELECT pricingStructure FROM PARKING_LOT JOIN PARKING_SPOT ON PARKING_LOT.id = PARKING_SPOT.parkingLotId WHERE PARKING_SPOT.id = ?",
                Long.class, parkingSpotId
        );
    }
}
