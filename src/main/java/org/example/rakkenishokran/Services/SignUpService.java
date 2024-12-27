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

 import java.util.Map;

 @RequiredArgsConstructor
 @Service
 public class SignUpService {

     private final JwtService jwtService;
     private final PasswordEncoder passwordEncoder;
     private final DriverRepository driverRepository;
     private final UserRepository userRepository;
     private final ParkingManagerRepository parkingManagerRepository;

     public ResponseEntity<Object> driverSignUp(DriverDTO signUpRequest) {

         try {
             String jwtToken = saveDriver(signUpRequest);
             return ResponseEntity.ok().body(
                     ResponseMessageDTO.builder()
                             .success(true)
                             .message("Driver registered successfully")
                             .statusCode(200)
                             .data(jwtToken)
                             .build());

         } catch (DataIntegrityViolationException e) {
             String errorMessage = extractConstraintMessage(e.getMessage());
             return ResponseEntity.status(HttpStatus.CONFLICT)
                     .body(ResponseMessageDTO.builder().success(false).message(errorMessage).statusCode(409).build());
         } catch (Exception e) {
             return ResponseEntity.internalServerError().body(ResponseMessageDTO.builder().success(false)
                     .message("An unexpected error occurred").statusCode(500).build());
         }
     }

     public ResponseEntity<Object> parkingManagerSignUp(ParkingManagerDTO signUpRequest) {

         try {
             String jwtToken = saveParkingManager(signUpRequest);
             return ResponseEntity.ok().body(
                     ResponseMessageDTO.builder()
                             .success(true)
                             .message("Manager registered successfully")
                             .statusCode(200)
                             .data(jwtToken)
                             .build());

         } catch (DataIntegrityViolationException e) {
             String errorMessage = extractConstraintMessage(e.getMessage());
             return ResponseEntity.status(HttpStatus.CONFLICT)
                     .body(ResponseMessageDTO.builder().success(false).message(errorMessage).statusCode(409).build());
         } catch (Exception e) {
             return ResponseEntity.internalServerError().body(ResponseMessageDTO.builder().success(false)
                     .message("An unexpected error occurred").statusCode(500).build());
         }
     }

     @Transactional
     public String saveParkingManager(ParkingManagerDTO signUpRequest) {
            var userParkingManager = User.builder()
                    .username(signUpRequest.getUsername())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .email(signUpRequest.getEmail())
                    .role(Role.LOT_MANAGER)
                    .phoneNumber(signUpRequest.getPhoneNumber())
                    .build();

            long userId = userRepository.save(userParkingManager);

            var parkingManager = ParkingManager.builder()
                    .id(userId)
                    .isApproved(false)
                    .build();
            parkingManagerRepository.saveToUnapproved(parkingManager);
            Map<String, Object> extraClaims = Map.of(
                    "role", userParkingManager.getRole().name(),
                    "username", userParkingManager.getUsername(),
                    "email", userParkingManager.getEmail(),
                    "isApproved", parkingManager.isApproved()
            );

            return jwtService.generateToken(extraClaims, userParkingManager);
     }

     @SuppressWarnings("Transaction Annotation")
     public String saveDriver(DriverDTO signUpRequest) {
         var userDriver = User.builder()
                 .username(signUpRequest.getUsername())
                 .password(passwordEncoder.encode(signUpRequest.getPassword()))
                 .email(signUpRequest.getEmail())
                 .role(Role.DRIVER)
                 .phoneNumber(signUpRequest.getPhoneNumber())
                 .build();

         long userId = userRepository.save(userDriver);

         var driver = Driver.builder()
                 .id(userId)
                 .licenseNumber(signUpRequest.getLicenseNumber())
                 .paymentMethod(signUpRequest.getPaymentMethod())
                 .build();
         driverRepository.save(driver);
         Map<String, Object> extraClaims = Map.of(
                 "role", userDriver.getRole().name(),
                 "username", userDriver.getUsername(),
                 "email", userDriver.getEmail()
         );

         return jwtService.generateToken(extraClaims, userDriver); // Changed from driver to userDriver
     }

     // return a more user-friendly error message
     private String extractConstraintMessage(String fullMessage) {
         int startIndex = fullMessage.indexOf("Key (");
         int endIndex = fullMessage.indexOf("already exists");
         if (startIndex != -1 && endIndex != -1) {
             // Extract the field name (e.g., email, username)

             String keyValuePair = fullMessage.substring(startIndex + "Key (".length(), endIndex);
             String fieldName = keyValuePair.split("=")[0].trim(); // Extract only the field name
             fieldName = fieldName.substring(fieldName.lastIndexOf(".") + 1); // Remove the package name
             // remove ) from the end of the field name
             fieldName = fieldName.substring(0, fieldName.length() - 1);
             return fieldName + " already exists"; // Simplify the message
         }
         return "Duplicate key error";
     }

 }