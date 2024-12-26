package org.example.rakkenishokran.Services;

import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.Config.JwtService;
import org.example.rakkenishokran.DTOs.DriverDTO;
import org.example.rakkenishokran.DTOs.ParkingManagerDTO;
import org.example.rakkenishokran.DTOs.ResponseMessageDTO;
import org.example.rakkenishokran.Entities.ParkingManager;
import org.example.rakkenishokran.Enums.Role;
import org.example.rakkenishokran.Repositories.DriverRepository;
import org.example.rakkenishokran.Repositories.ParkingManagerRepository;
import org.example.rakkenishokran.Repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.example.rakkenishokran.Entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.example.rakkenishokran.Entities.Driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ParkingManagerService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final ParkingManagerRepository parkingManagerRepository;

    public ResponseEntity<Object> getPendingApproval() {

        try {
            List<Optional<User>> pendingApproval = parkingManagerRepository.findAllUnapproved();
            return ResponseEntity.ok().body(
                    ResponseMessageDTO.builder()
                            .success(true)
                            .message("Pending Approval retrieved successfully")
                            .statusCode(200)
                            .data(pendingApproval)
                            .build());


        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseMessageDTO.builder().success(false)
                    .message("An unexpected error occurred").statusCode(500).build());
        }
    }
    public ResponseEntity<Object> approve(long managerId){

        try {
            List<Optional<User>> pendingApproval = parkingManagerRepository.findAllUnapproved();
            return ResponseEntity.ok().body(
                    ResponseMessageDTO.builder()
                            .success(true)
                            .message("Driver registered successfully")
                            .statusCode(200)
                            .data(pendingApproval)
                            .build());


        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseMessageDTO.builder().success(false)
                    .message("An unexpected error occurred").statusCode(500).build());
        }
    }
}