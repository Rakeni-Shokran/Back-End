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
        jdbcTemplate.update("INSERT INTO tokens (token, user_id, revoked) VALUES (?, ?, ?)",
                token.getToken(), token.getUser().getId(), token.isRevoked());
    }
    
    //find by token 
    public Optional<Token> findByToken(String token) {
        return jdbcTemplate.query("SELECT * FROM tokens WHERE token = ?",
                new Object[]{token},
                (rs, rowNum) -> Optional.of(new Token(rs.getString("token"), rs.getBoolean("revoked")))).stream().findFirst().orElse(null);
    }



}