package org.example.rakkenishokran.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class DriverDTO {

    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String paymentMethod;
    private String licenseNumber;

}
