package org.example.rakkenishokran.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.rakkenishokran.Enums.Role;


@Data
@Builder
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String phoneNumber;
    private String token;
}
