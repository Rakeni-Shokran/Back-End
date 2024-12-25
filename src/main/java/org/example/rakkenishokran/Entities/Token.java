package org.example.rakkenishokran.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Token {
    Integer id;
    String token;
    boolean revoked;
    User user;

    public Token(String token, boolean revoked) {
        this.token = token;
        this.revoked = revoked;
    }
}
