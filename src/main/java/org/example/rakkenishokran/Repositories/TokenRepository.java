package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TokenRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Token token) {
        jdbcTemplate.update("INSERT INTO TOKEN (token, userId, revoked) VALUES (?, ?, ?)",
                token.getToken(), token.getUser().getId(), token.isRevoked());
    }

    public Optional<Token> findByToken(String token) {
        return jdbcTemplate.query("SELECT * FROM TOKEN WHERE token = ?",
                (rs, rowNum) -> new Token(
                        rs.getInt("id"),
                        rs.getString("token"),
                        null,
                        rs.getBoolean("revoked")
                ),
                token
        ).stream().findFirst();
    }
}