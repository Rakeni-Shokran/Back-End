package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.ParkingManager;
import org.example.rakkenishokran.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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

    public void save(long ParkingManagerId) {
        jdbcTemplate.update("INSERT INTO PARKING_MANAGER (id) VALUES (?)",
                ParkingManagerId
        );
    }
    public void saveToUnapproved(ParkingManager ParkingManager) {
        jdbcTemplate.update("INSERT INTO UNAPPROVED_PARKING_MANAGER (id) VALUES (?)",
                ParkingManager.getId()
        );
    }

    public Optional<User> findUnapprovedById(long id) {

        String sql = "SELECT * FROM UNAPPROVED_PARKING_MANAGER LEFT JOIN USER ON UNAPPROVED_PARKING_MANAGER.id= USER.id WHERE USER.id = ? ";
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                            User.builder()
                                    .id(rs.getInt("id"))
                                    .username(rs.getString("name"))
                                    .password(rs.getString("password"))
                                    .email(rs.getString("email"))
                                    .build()
                    )
            );
        } catch (Exception e) {
            System.err.println("Error executing query: " + sql + " with id: " + id);
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public List<User> findAllUnapproved() {
            String sql = "SELECT * FROM UNAPPROVED_PARKING_MANAGER " +
                    "LEFT JOIN USER ON UNAPPROVED_PARKING_MANAGER.id = USER.id";

            try {
                return (List<User>) jdbcTemplate.query(sql, (rs, rowNum) ->
                        User.builder()
                                .id(rs.getInt("id"))
                                .username(rs.getString("name"))
                                .password(rs.getString("password"))
                                .email(rs.getString("email"))
                                .build()
                );
            } catch (Exception e) {
                System.err.println("Error executing query: " + sql);
                e.printStackTrace();
                return Collections.emptyList(); // Return an empty list on failure
            }
    }

    public void deleteFromUnApproved(long parkingManagerId) {
        String sql = "DELETE FROM UNAPPROVED_PARKING_MANAGER WHERE id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, parkingManagerId);
            if (rowsAffected == 0) {
                throw new EmptyResultDataAccessException(
                        String.format("No unapproved parking manager found with id: %d", parkingManagerId), 1);
            }
        } catch (Exception e) {
            System.out.println("Error executing query: " + sql + " with id: " + parkingManagerId);
            throw e;
        }
    }

    public void approve(long id) {
        try {
            // First verify the user exists in UNAPPROVED_PARKING_MANAGER
            String checkSql = "SELECT COUNT(*) FROM UNAPPROVED_PARKING_MANAGER WHERE id = ?";
            int count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);

            if (count == 0) {
                throw new EmptyResultDataAccessException(
                        String.format("No unapproved parking manager found with id: %d", id), 1);
            }

            // Insert into PARKING_MANAGER
            String insertSql = "INSERT INTO PARKING_MANAGER (id) VALUES (?)";
            jdbcTemplate.update(insertSql, id);

            // Delete from UNAPPROVED_PARKING_MANAGER
            deleteFromUnApproved(id);

        } catch (Exception e) {
            throw e;
        }
    }

}


