package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM USER", (rs, rowNum) ->
                new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("phoneNumber")
                ));
    }

    public Optional<User> findByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM USER WHERE email = ?",
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("phoneNumber")
                ),
                email
        );
        if (users.isEmpty())
            throw new EmptyResultDataAccessException(1);
        else
            return Optional.of(users.get(0));
    }

    public Optional<User> findByUsername(String username) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM USER WHERE name = ?",
                (rs, rowNum) -> new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("phoneNumber")
                ),
                username
        );
        if (users.isEmpty())
            throw new EmptyResultDataAccessException(1);
        else
            return Optional.of(users.get(0));
    }

    public void save(User user) {
        jdbcTemplate.update("INSERT INTO USER (name, phoneNumber, email, password) VALUES (?, ?, ?, ?)",
                user.getName(),user.getPhoneNumber(), user.getEmail(), user.getPassword());
    }

    public void update(User user) {
        jdbcTemplate.update("UPDATE USER SET name = ?, phoneNumber = ?, email = ?, password = ? WHERE id = ?",
                user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword(), user.getId());
    }

    public void deleteById(User user) {
        jdbcTemplate.update("DELETE FROM USER WHERE id = ?", user.getId());
    }

    public void deleteByEmail(User user) {
        jdbcTemplate.update("DELETE FROM USER WHERE email = ?", user.getEmail());
    }

    public void deleteByUsername(User user) {
        jdbcTemplate.update("DELETE FROM USER WHERE name = ?", user.getName());
    }

}