package org.example.rakkenishokran.Services;

import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.DTOs.*;
import org.example.rakkenishokran.Entities.ParkingSpot;
import org.example.rakkenishokran.Entities.Reservation;
import org.example.rakkenishokran.Entities.User;
import org.example.rakkenishokran.Repositories.DriverRepository;
import org.example.rakkenishokran.Repositories.ParkingSpotRepository;
import org.example.rakkenishokran.Repositories.ReservationRepository;
import org.example.rakkenishokran.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;
    private final DriverRepository driverRepository;

    public ResponseEntity<Object> getAvailableSpots(ParkingRequestDTO request) {

        String location = request.getLocation();
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        try {
            List<ParkingSpot> availableParkingSpots = parkingSpotRepository.findFreeParkingSpotsInTimeStampRange(location, startTime, endTime);

            if(availableParkingSpots.isEmpty()){
                return ResponseEntity.badRequest().body(ResponseMessageDTO.builder()
                        .success(false)
                        .message("No Spots available with the given parameters")
                        .statusCode(404)
                        .build());
            }

            List<ParkingSpotDTO> parkingSpotDTOS = new ArrayList<>();
            for(ParkingSpot parkingSpot : availableParkingSpots){
                ParkingSpotDTO parkingSpotDTO = ParkingSpotDTO.builder()
                        .id(parkingSpot.getId())
                        .parkingLotId(parkingSpot.getParkingLotId())
                        .status(parkingSpot.getStatus())
                        .type(parkingSpot.getType())
                        .build();
                parkingSpotDTOS.add(parkingSpotDTO);
            }
            System.out.println("Parking Spot DTOs: " + parkingSpotDTOS);

            return ResponseEntity.ok().body(
                    ResponseMessageDTO.builder()
                            .success(true)
                            .message("Available parking spots retrieved successfully")
                            .statusCode(200)
                            .data(parkingSpotDTOS)
                            .build());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ResponseMessageDTO.builder().success(false)
                    .message("An unexpected error occurred").statusCode(500).build());
        }
//       return ResponseEntity.ok(parkingSpots);
    }

    public synchronized ResponseEntity<Object> reserveParkingSpot(ReservationRequestDTO request) {

        try {
            long parkingSpotId = request.getParkingSpotId();
            long driverId = request.getDriverId();
            String startTime = request.getStartTime();
            String endTime = request.getEndTime();

            // Get the pricing structure for the parking lot that the parking spot belongs to
            long baseRate = parkingSpotRepository.getLotBaseRate(parkingSpotId);

            // Get the duration of the reservation in hours
            Timestamp startTimeStamp = Timestamp.valueOf(startTime);
            Timestamp endTimeStamp = Timestamp.valueOf(endTime);
            int durationInHours = (int) ((endTimeStamp.getTime() - startTimeStamp.getTime()) / (1000 * 60 * 60));

            long scalar = getScalarForTimestamp(startTimeStamp);
            // Calculate the total cost of the reservation
            long totalCost = baseRate * scalar + durationInHours * baseRate * scalar;

            // Save the reservation to the database
            reservationRepository.saveReservation(parkingSpotId, driverId, startTime, endTime, totalCost,false);
            User driver = driverRepository.findById(driverId);
            Reservation reservation = Reservation.builder()
                    .startTimeStamp(startTimeStamp)
                    .endTimeStamp(endTimeStamp)
                    .parkingSpotId(parkingSpotId)
                    .build();
            notificationService.sendReservationConfirmationEmail(driver,reservation);
            return ResponseEntity.ok().body(ResponseMessageDTO.builder().success(true)
                    .message("Parking spot reserved successfully").statusCode(200).build());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ResponseMessageDTO.builder().success(false)
                    .message("An unexpected error occurred").statusCode(500).build());
        }
    }

    private static long getScalarForTimestamp(Timestamp timestamp) {
        LocalTime time = timestamp.toLocalDateTime().toLocalTime();

        if (time.isBefore(LocalTime.of(6, 0))) {
            return (long) 0.8;
        } else if (time.isBefore(LocalTime.of(12, 0))) {
            return (long) 1.0;
        } else if (time.isBefore(LocalTime.of(18, 0))) {
            return 2;
        } else {
            return (long) 1.5;
        }
    }

    public List<DriverReservationDTO> findAllByDriverIdFromNow(long driverId) {
        return reservationRepository.findAllByDriverIdFromNow(driverId);
    }
}
