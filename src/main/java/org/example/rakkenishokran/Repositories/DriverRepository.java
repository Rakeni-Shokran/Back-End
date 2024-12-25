package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.Driver;
import org.example.rakkenishokran.Entities.User;
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


    public void save(Driver driver) {
        jdbcTemplate.update("INSERT INTO USER (name, phoneNumber, email, password) VALUES (?, ?, ?, ?)",
                driver.getUsername(),driver.getPhoneNumber(), driver.getEmail(), driver.getPassword());
    }

}
