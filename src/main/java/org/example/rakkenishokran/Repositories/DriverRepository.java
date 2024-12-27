package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.Driver;
import org.example.rakkenishokran.Entities.User;
import org.example.rakkenishokran.Enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DriverRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        String sql = "SELECT " +
                "USER.id, " +
                "USER.name AS username, " +
                "USER.password, " +
                "USER.email, " +
                "USER.phoneNumber, " +
                "USER.role " +
                "FROM USER " +
                "INNER JOIN DRIVER ON USER.id = DRIVER.id " +
                "WHERE USER.role = 'DRIVER'";

        List<User> drivers = jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getString("phoneNumber"),
                        Role.valueOf(rs.getString("role")) // Ensure your User model has a 'role' field if needed
                )
        );

        if (drivers.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return drivers;
    }


    public User findById(long id) {
        String sql = "SELECT " +
                "USER.id, " +
                "USER.name AS username, " +
                "USER.password, " +
                "USER.email, " +
                "USER.phoneNumber, " +
                "USER.role " +
                "FROM USER " +
                "INNER JOIN DRIVER ON USER.id = DRIVER.id " +
                "WHERE USER.id = ?";

        List<User> drivers = jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getString("phoneNumber"),
                        Role.valueOf(rs.getString("role")) // Ensure your User model has a 'role' field if needed
                ),
                id
        );

        if (drivers.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return drivers.getFirst();

    }

    public Optional<Driver> findByEmail(String email){
        List<Driver> drivers = jdbcTemplate.query(
                "SELECT * FROM DRIVER " +
                        "LEFT JOIN USER " +
                        "ON DRIVER.id = USER.id " +
                        "WHERE USER.email = ?",
                (rs, rowNum) -> new Driver(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("payment"),
                        rs.getString("license")
                ),
                email
        );
        if (drivers.isEmpty())
            throw new EmptyResultDataAccessException(1);
        else
            return Optional.of(drivers.get(0));
    }

    public Optional<Driver> findByUsername(String username){
        List<Driver> drivers = jdbcTemplate.query(
                "SELECT * FROM DRIVER " +
                        "LEFT JOIN USER " +
                        "ON DRIVER.id = USER.id " +
                        "WHERE USER.name = ?",
                (rs, rowNum) -> new Driver(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("payment"),
                        rs.getString("license")
                ),
                username
        );
        if (drivers.isEmpty())
            throw new EmptyResultDataAccessException(1);
        else
            return Optional.of(drivers.get(0));
    }

    public void save(Driver driver) {
        jdbcTemplate.update("INSERT INTO DRIVER (id, payment, license) VALUES (?, ?, ?)",
                driver.getId(),
                driver.getPaymentMethod(),
                driver.getLicenseNumber()
        );
    }

}