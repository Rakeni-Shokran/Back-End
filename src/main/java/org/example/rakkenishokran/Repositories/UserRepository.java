package org.example.rakkenishokran.Repositories;

import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.Entities.User;
import org.example.rakkenishokran.Enums.Role;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM USER", (rs, rowNum) ->
                new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        Role.valueOf(rs.getString("role"))
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
                        rs.getString("phoneNumber"),
                        Role.valueOf(rs.getString("role"))
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
                        rs.getString("phoneNumber"),
                        Role.valueOf(rs.getString("role"))
                ),
                username
        );
        if (users.isEmpty())
            throw new EmptyResultDataAccessException(1);
        else
            return Optional.of(users.get(0));
    }

    public long save(User user) {
        System.out.println(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USER (name, phoneNumber, email, password, role) VALUES (?, ?, ?, ?, ?)", new String[] {"id"});
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole().name());
            return ps;
        }, keyHolder);
        System.out.println(keyHolder.getKey());
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void update(User user) {
        jdbcTemplate.update("UPDATE USER SET name = ?, phoneNumber = ?, email = ?, password = ? WHERE id = ?",
                user.getUsername(), user.getPhoneNumber(), user.getEmail(), user.getPassword(), user.getId());
    }

    public void deleteById(long userId) {
        jdbcTemplate.update("DELETE FROM USER WHERE id = ?", userId);
    }

    public void deleteByEmail(User user) {
        jdbcTemplate.update("DELETE FROM USER WHERE email = ?", user.getEmail());
    }

    public void deleteByUsername(User user) {
        jdbcTemplate.update("DELETE FROM USER WHERE name = ?", user.getUsername());
    }
}