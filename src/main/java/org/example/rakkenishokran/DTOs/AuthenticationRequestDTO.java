package org.example.rakkenishokran.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AuthenticationRequestDTO {
    private String username;
    
    private String email;
    
    private String password;
}
