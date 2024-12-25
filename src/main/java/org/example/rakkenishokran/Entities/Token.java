package org.example.rakkenishokran.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Token {
    int id;
    String token;
    User user;
    boolean revoked;
}
