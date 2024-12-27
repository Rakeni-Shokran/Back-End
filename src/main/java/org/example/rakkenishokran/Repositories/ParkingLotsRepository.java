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
}
