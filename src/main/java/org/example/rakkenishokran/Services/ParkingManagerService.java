package org.example.rakkenishokran.Services;

import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.Config.JwtService;
import org.example.rakkenishokran.DTOs.DriverDTO;
import org.example.rakkenishokran.DTOs.ParkingManagerDTO;
import org.example.rakkenishokran.DTOs.ResponseMessageDTO;
import org.example.rakkenishokran.Entities.ParkingManager;
import org.example.rakkenishokran.Enums.Role;
import org.example.rakkenishokran.Repositories.DriverRepository;
import org.example.rakkenishokran.Repositories.ParkingLotsRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ParkingManagerService {

    private final UserRepository userRepository;
    private final ParkingManagerRepository parkingManagerRepository;
    private final ParkingLotsRepository parkingLotsRepository;

    public ResponseEntity<Object> getPendingApproval() {

        try {
            List<User> pendingApproval = parkingManagerRepository.findAllUnapproved();
            if(pendingApproval.isEmpty()){
                return ResponseEntity.badRequest().body(ResponseMessageDTO.builder()
                        .success(false)
                        .message("No pending approval found")
                        .statusCode(404)
                        .build());
            }
            List<ParkingManagerDTO> pendingApprovalDTOs = new ArrayList<>();
            for(User user : pendingApproval){
                System.out.println("User: " + user.getId());
                String lotName = parkingManagerRepository.getLotNameByUnApprovedManagerId(user.getId());
                System.out.println("Lot name: " + lotName);
                ParkingManagerDTO parkingManagerDTO = ParkingManagerDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .lotName(lotName)
                        .build();
                pendingApprovalDTOs.add(parkingManagerDTO);
            }
            System.out.println("Pending approval DTOs: " + pendingApprovalDTOs);

            return ResponseEntity.ok().body(
                    ResponseMessageDTO.builder()
                            .success(true)
                            .message("Pending Approval retrieved successfully")
                            .statusCode(200)
                            .data(pendingApprovalDTOs)
                            .build());


        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseMessageDTO.builder().success(false)
                    .message("An unexpected error occurred").statusCode(500).build());
        }
    }
    public ResponseEntity<Object> approve(long managerId) {
        try {
            Optional<User> userOptional = parkingManagerRepository.findUnapprovedById(managerId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ResponseMessageDTO.builder()
                                .success(false)
                                .message("Parking manager not found in unapproved list")
                                .statusCode(404)
                                .build());
            }
            String lotName = parkingManagerRepository.getLotNameByUnApprovedManagerId(managerId);
            parkingManagerRepository.approve(managerId);
            parkingLotsRepository.updateParkingLotManager(lotName, managerId);
            ParkingManagerDTO parkingManagerDTO = ParkingManagerDTO.builder()
                    .id(userOptional.get().getId())
                    .username(userOptional.get().getUsername())
                    .email(userOptional.get().getEmail())
                    .lotName(lotName)
                    .build();
            return ResponseEntity.ok()
                    .body(ResponseMessageDTO.builder()
                            .success(true)
                            .message("Parking manager approved successfully")
                            .statusCode(200)
                            .data(parkingManagerDTO)
                            .build());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseMessageDTO.builder()
                            .success(false)
                            .message("Failed to approve parking manager")
                            .statusCode(500)
                            .build());
        }
    }
    public ResponseEntity<Object> decline(long managerId) {
        try {
            Optional<User> userOptional = parkingManagerRepository.findUnapprovedById(managerId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ResponseMessageDTO.builder()
                                .success(false)
                                .message("Parking manager not found in unapproved list")
                                .statusCode(404)
                                .build());
            }

            parkingManagerRepository.deleteFromUnApproved(managerId);
            userRepository.deleteById(managerId);
            ParkingManagerDTO parkingManagerDTO = ParkingManagerDTO.builder()
                    .id(userOptional.get().getId())
                    .username(userOptional.get().getUsername())
                    .email(userOptional.get().getEmail())
                    .build();
            return ResponseEntity.ok()
                    .body(ResponseMessageDTO.builder()
                            .success(true)
                            .message("Parking manager deleted successfully")
                            .statusCode(200)
                            .data(parkingManagerDTO)
                            .build());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ResponseMessageDTO.builder()
                            .success(false)
                            .message("Failed to delete parking manager")
                            .statusCode(500)
                            .build());
        }
    }
}