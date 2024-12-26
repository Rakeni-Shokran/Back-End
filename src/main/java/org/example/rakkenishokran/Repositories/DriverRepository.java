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
        System.out.println("Here");
        System.out.println(driver.getPaymentMethod());
        System.out.println(driver.getLicenseNumber());
        System.out.println(driver.getUserId());
        jdbcTemplate.update("INSERT INTO DRIVER (payment, license, userId) VALUES (?, ?, ?)",
                driver.getPaymentMethod(),
                driver.getLicenseNumber(),
                driver.getUserId()
        );
        System.out.println("There");
    }
}