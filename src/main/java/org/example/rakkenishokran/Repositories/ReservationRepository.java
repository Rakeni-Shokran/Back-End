package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.DTOs.DriverReservationDTO;
import org.example.rakkenishokran.Entities.Driver;
import org.example.rakkenishokran.Entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean saveReservationProcedure(long parkingSpotId, long driverId, String startTime, String endTime, long totalCost, boolean isReminded) {
        String sql = "{CALL SaveReservation(?, ?, ?, ?, ?, ?, ?)}";
        return Boolean.TRUE.equals(jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setLong(1, parkingSpotId);
            cs.setLong(2, driverId);
            cs.setString(3, startTime);
            cs.setString(4, endTime);
            cs.setLong(5, totalCost);
            cs.setBoolean(6, isReminded);
            cs.registerOutParameter(7, Types.BOOLEAN);
            cs.execute();
            return cs.getBoolean(7);
        }));
    }

    public void saveReservation(long parkingSpotId, long driverId, String startTime, String endTime, long totalCost, boolean isReminded) {
            boolean success = saveReservationProcedure(parkingSpotId, driverId, startTime, endTime, totalCost, isReminded);
            if (!success) {
                throw new RuntimeException("Failed to save reservation: Parking spot is already taken.");
            }
    }

    public void updateReservation(String startTime, String endTime, long parkingSpotId, boolean isReminded){
        jdbcTemplate.update("UPDATE RESERVATION SET startTimeStamp = ?, endTimeStamp = ?, isReminded = ? WHERE parkingSpotId = ?",
                startTime, endTime, isReminded, parkingSpotId
        );
    }

    public List<Reservation> findAllByDriverId(long driverId) {
        return jdbcTemplate.query(
                "SELECT * FROM RESERVATION WHERE userId = ?",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getTimestamp("startTimeStamp"),
                        rs.getTimestamp("endTimeStamp"),
                        rs.getInt("price"),
                        rs.getLong("userId"),
                        rs.getLong("parkingSpotId"),
                        rs.getBoolean("isReminded")
                ),
                driverId
        );
    }

    public List<DriverReservationDTO> findAllByDriverIdFromNow(long driverId) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return jdbcTemplate.query(
                "SELECT DISTINCT RESERVATION.id, PARKING_LOT.id AS parkingLotId, PARKING_LOT.location, PARKING_LOT.name, PARKING_LOT.pricingStructure, RESERVATION.startTimeStamp, RESERVATION.endTimeStamp, PARKING_SPOT.id AS parkingSpotId FROM PARKING_LOT " +
                    "JOIN PARKING_SPOT ON PARKING_LOT.id = PARKING_SPOT.parkingLotId " +
                    "JOIN RESERVATION ON PARKING_SPOT.id = RESERVATION.parkingSpotId " +
                    "WHERE RESERVATION.userId = ? AND RESERVATION.startTimeStamp >= ?",
                (rs, rowNum) -> new DriverReservationDTO(
                        rs.getLong("id"),
                        rs.getLong("parkingLotId"),
                        rs.getString("location"),
                        rs.getString("name"),
                        rs.getInt("pricingStructure"),
                        rs.getTimestamp("startTimeStamp"),
                        rs.getTimestamp("endTimeStamp"),
                        rs.getLong("parkingSpotId")
                ),
                driverId, now
        );
    }
}