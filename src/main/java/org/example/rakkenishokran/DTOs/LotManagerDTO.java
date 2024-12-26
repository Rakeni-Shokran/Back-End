package org.example.rakkenishokran.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor@RequiredArgsConstructor
@Builder
@Data
public class LotManagerDTO {
    private String lotName;
    private String lotLocation;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;



}
