package org.example.rakkenishokran.Controllers;

import org.example.rakkenishokran.Authorization.AuthenticationResponse;

import org.example.rakkenishokran.DTOs.AuthenticationRequestDTO;
import org.example.rakkenishokran.DTOs.DriverDTO;
import org.example.rakkenishokran.DTOs.ParkingManagerDTO;
import org.example.rakkenishokran.Services.AuthenticationService;
import org.example.rakkenishokran.Services.SignUpService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor //lombok annotation to create a constructor with all the required fields
@Getter
 
@CrossOrigin
public class
RegistrationController {

    private final SignUpService signUpService;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationService authenticationService;

    @PostMapping("/register/driver")
    public ResponseEntity<Object> registerDriver(@RequestBody DriverDTO request) {
        System.out.println("request = " + request);
        return signUpService.driverSignUp(request);
    }

    @PostMapping("/register/manager")

    public ResponseEntity<Object> registerDoctor(@RequestBody ParkingManagerDTO request) {
        System.out.println("request = " + request);
        return signUpService.parkingManagerSignUp(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequestDTO request) {
        return authenticationService.authenticate(request);
    }



    // if the user is logged in and its token is expired,
    // this endpoint will be called to refresh the token
    @GetMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestBody AuthenticationResponse token) {
        System.out.println(token);
        return authenticationService.refreshToken(token.getRefreshToken());
    }




}
