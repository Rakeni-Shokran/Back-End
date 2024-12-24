package org.example.rakkenishokran.Entities;

import lombok.Data;

@Data
public class Token {
    int id;
    String token;
    User user;
    boolean revoked;
}
