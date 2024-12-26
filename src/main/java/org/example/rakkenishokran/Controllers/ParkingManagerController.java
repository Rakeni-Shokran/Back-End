package org.example.rakkenishokran.Controllers;

import org.example.rakkenishokran.Authorization.AuthenticationResponse;

import org.example.rakkenishokran.DTOs.AuthenticationRequestDTO;
import org.example.rakkenishokran.DTOs.DriverDTO;
import org.example.rakkenishokran.DTOs.ParkingManagerDTO;
import org.example.rakkenishokran.Services.AuthenticationService;
import org.example.rakkenishokran.Services.ParkingManagerService;
import org.example.rakkenishokran.Services.SignUpService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pm")
@RequiredArgsConstructor //lombok annotation to create a constructor with all the required fields
@Getter

@CrossOrigin
public class
ParkingManagerController {

    private final ParkingManagerService parkingManagerService;

    @GetMapping("/getPendingApproval")
    public ResponseEntity<Object> getPendingApprove() {
        System.out.println("Getting pending approval");
        return  parkingManagerService.getPendingApproval();
    }

    @PutMapping("/approve/{managerId}")
    public ResponseEntity<Object> approveManager(@PathVariable long managerId) {
        return  parkingManagerService.approve(managerId);
    }
    @PutMapping("/decline/{managerId}")
    public ResponseEntity<Object> decline(@PathVariable long managerId) {
        return  parkingManagerService.decline(managerId);
    }






}
