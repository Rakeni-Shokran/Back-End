package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.SysAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SysAdminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<SysAdmin> findById(long id) {
        List<SysAdmin> drivers = jdbcTemplate.query(
                "SELECT * FROM PARKING_MANAGER WHERE id = ?",
                (rs, rowNum) -> new SysAdmin(
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

    public Optional<SysAdmin> findByEmail(String email){
        List<SysAdmin> drivers = jdbcTemplate.query(
                "SELECT * FROM PARKING_MANAGER " +
                        "LEFT JOIN USER " +
                        "ON PARKING_MANAGER.id = USER.id " +
                        "WHERE USER.email = ?",
                (rs, rowNum) -> new SysAdmin(
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

    public Optional<SysAdmin> findByUsername(String username){
        List<SysAdmin> drivers = jdbcTemplate.query(
                "SELECT * FROM PARKING_MANAGER " +
                        "LEFT JOIN USER " +
                        "ON PARKING_MANAGER.id= USER.id " +
                        "WHERE USER.name = ?",
                (rs, rowNum) -> new SysAdmin(
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

    public void save(SysAdmin SysAdmin) {
        jdbcTemplate.update("INSERT INTO PARKING_MANAGER (id) VALUES (?)",
                SysAdmin.getId()
        );
    }

}
