package org.example.rakkenishokran.Services;


import org.example.rakkenishokran.Config.JwtService;
import org.example.rakkenishokran.DTOs.*;
import org.example.rakkenishokran.Entities.User;
import org.example.rakkenishokran.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.example.rakkenishokran.DTOs.ResponseMessageDTO;
import org.example.rakkenishokran.DTOs.AuthenticationRequestDTO;


import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;



    public ResponseEntity<Object> authenticate(AuthenticationRequestDTO request) {
        try {
            if (request.getUsername() == null || request.getPassword() == null || 
                request.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Username and password are required");
            }

            System.out.println("Attempting authentication for user: " + request.getUsername());
            System.out.println("User password: " + request.getPassword());

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            System.out.println("Found user: " + user.getUsername());
            System.out.println("User password hash: " + user.getPassword());
            System.out.println("User role: " + user.getRole());

            // This will throw an exception if authentication fails
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            System.out.println("sss");

            Map<String, Object> claims = Map.of("role", user.getRole().toString(),
                    "email", user.getEmail(),
                    "username", user.getUsername()
            )
                    ;
            var token = jwtService.generateToken(claims, user);

            return ResponseEntity.ok().body(
                    ResponseMessageDTO.builder()
                            .message("User authenticated successfully")
                            .success(true)
                            .statusCode(200)
                            .data(token)
                            .build());

        }
        catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseMessageDTO.builder()
                            .message(e.getMessage())
                            .success(false)
                            .statusCode(401)
                            .data(null)
                            .build());
        }

    }


    public ResponseEntity<Object> refreshToken(String token) {
        try {
            String userEmail = jwtService.extractUsername(token);
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Map<String, Object> claims = Map.of("role", user.getRole().toString(),
                    "username", user.getUsername(),
                    "email", user.getEmail()
            );

            var newToken = jwtService.generateToken(claims, user);
            return ResponseEntity.ok().body(ResponseMessageDTO.builder().message("Token refreshed successfully").success(true).statusCode(200).data(newToken).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(ResponseMessageDTO.builder()
                            .message(e.getMessage())
                            .success(false)
                            .statusCode(404)
                            .data(null).build());
        }
    }





}
