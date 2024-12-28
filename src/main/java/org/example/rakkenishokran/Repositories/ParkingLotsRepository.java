package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.ParkingLot;
import org.example.rakkenishokran.Entities.ParkingLotReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ParkingLotsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ParkingLot> findAllParkingLotsNames() {
        return jdbcTemplate.query(
                "SELECT id, name, location FROM PARKING_LOT",
                (rs, rowNum) -> new ParkingLot(
                        rs.getLong("id"),
                        rs.getString("name")
                )
        );
    }

    public List<ParkingLotReport> getParkingLotReports() {
        String sql = "SELECT " +
                "pl.id AS parkingLotId, " +
                "pl.name AS parkingLotName, " +
                "pl.location, " +
                "pl.capacity AS totalSpots, " +  // capacity and totalSpots are the same
                "COUNT(ps.id) AS totalSpots, " +
                "SUM(CASE WHEN ps.status = 'Occupied' THEN 1 ELSE 0 END) AS occupiedSpots, " +
                "COUNT(ps.id) - SUM(CASE WHEN ps.status = 'Occupied' THEN 1 ELSE 0 END) AS availableSpots, " +
                "(COUNT(ps.id) - SUM(CASE WHEN ps.status = 'Occupied' THEN 1 ELSE 0 END)) / COUNT(ps.id) * 100 AS occupancyRate, " +
                "SUM(r.price) AS revenue, " +  // Calculating revenue from reservations
                "pm.id AS parkingManagerId, " +
                "u.name AS parkingManagerName, " +
                "u.email AS parkingManagerEmail " +
                "FROM PARKING_LOT pl " +
                "JOIN PARKING_SPOT ps ON pl.id = ps.parkingLotId " +
                "LEFT JOIN RESERVATION r ON ps.id = r.parkingSpotId " +  // Left join to include all parking spots, even if no reservations exist
                "JOIN PARKING_MANAGER pm ON pl.parkingManagerId = pm.id " +
                "JOIN USER u ON pm.id = u.id " +
                "GROUP BY pl.id, pl.name, pl.location, pl.capacity, pm.id, u.name, u.email";

        return jdbcTemplate.query(sql, new RowMapper<ParkingLotReport>() {
            @Override
            public ParkingLotReport mapRow(ResultSet rs, int rowNum) throws SQLException {
                ParkingLotReport report = new ParkingLotReport();
                report.setParkingLotId(rs.getLong("parkingLotId"));
                report.setParkingLotName(rs.getString("parkingLotName"));
                report.setLocation(rs.getString("location"));
                report.setTotalSpots(rs.getInt("totalSpots"));
                report.setOccupiedSpots(rs.getInt("occupiedSpots"));
                report.setAvailableSpots(rs.getInt("availableSpots"));
                report.setOccupancyRate(rs.getDouble("occupancyRate"));
                report.setRevenue(rs.getDouble("revenue"));
                report.setParkingManagerId(rs.getLong("parkingManagerId"));
                report.setParkingManagerName(rs.getString("parkingManagerName"));
                report.setParkingManagerEmail(rs.getString("parkingManagerEmail"));
                return report;
            }
        });
    }

}
