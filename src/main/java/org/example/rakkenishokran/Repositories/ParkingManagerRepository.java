package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.ParkingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ParkingManagerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<ParkingManager> findById(long id) {
        List<ParkingManager> drivers = jdbcTemplate.query(
                "SELECT * FROM PARKING_MANAGER WHERE id = ?",
                (rs, rowNum) -> new ParkingManager(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                ),
                id
        );
        if (drivers.isEmpty())
            throw new EmptyResultDataAccessException(1);
        else
            return Optional.of(drivers.get(0));
    }

    public Optional<ParkingManager> findByEmail(String email){
        List<ParkingManager> drivers = jdbcTemplate.query(
                "SELECT * FROM PARKING_MANAGER " +
                        "LEFT JOIN USER " +
                        "ON PARKING_MANAGER.id = USER.id " +
                        "WHERE USER.email = ?",
                (rs, rowNum) -> new ParkingManager(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                ),
                email
        );
        if (drivers.isEmpty())
            throw new EmptyResultDataAccessException(1);
        else
            return Optional.of(drivers.get(0));
    }

    public Optional<ParkingManager> findByUsername(String username){
        List<ParkingManager> drivers = jdbcTemplate.query(
                "SELECT * FROM PARKING_MANAGER " +
                        "LEFT JOIN USER " +
                        "ON PARKING_MANAGER.id= USER.id " +
                        "WHERE USER.name = ?",
                (rs, rowNum) -> new ParkingManager(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phoneNumber")
                ),
                username
        );
        if (drivers.isEmpty())
            throw new EmptyResultDataAccessException(1);
        else
            return Optional.of(drivers.get(0));
    }

    public void save(ParkingManager ParkingManager) {
        jdbcTemplate.update("INSERT INTO PARKING_MANAGER (id) VALUES (?)",
                ParkingManager.getId()
        );
    }

}
