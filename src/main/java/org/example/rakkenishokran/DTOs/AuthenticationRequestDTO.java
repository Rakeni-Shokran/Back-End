package org.example.rakkenishokran.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AuthenticationRequestDTO {
    private String username;
    private String email;
    private String password;

}
