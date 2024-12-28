package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public String findParkingLotNameByManagerId(long managerId){
        return jdbcTemplate.queryForObject(
                "SELECT name FROM PARKING_LOT WHERE parkingManagerId = ?",
                (rs, rowNum) -> rs.getString("name"),
                managerId
        );
    }
    public void updateParkingLotManager(String lotName, long managerId) {
        jdbcTemplate.update(
                "UPDATE PARKING_LOT SET parkingManagerId = ? WHERE name = ?",
                managerId,
                lotName
        );
    }

    public List<ParkingLot> findAllParkingLots() {
        return jdbcTemplate.query(
                "SELECT * FROM PARKING_LOT",
                (rs, rowNum) -> new ParkingLot(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getInt("capacity"),
                        rs.getInt("pricingStructure"),
                        rs.getLong("parkingManagerId")
                )
        );
    }
}
