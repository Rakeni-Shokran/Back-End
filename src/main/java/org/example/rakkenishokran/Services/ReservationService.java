package org.example.rakkenishokran.Services;

import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.DTOs.*;
import org.example.rakkenishokran.Entities.ParkingSpot;
import org.example.rakkenishokran.Entities.User;
import org.example.rakkenishokran.Repositories.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ParkingSpotRepository parkingSpotRepository;

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

    public synchronized void reserveParkingSpot(ReservationRequestDTO request) {

        long parkingSpotId = request.getParkingSpotId();
        long driverId = request.getDriverId();
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();

        // Get the pricing structure for the parking lot that the parking spot belongs to
        int baseRate = parkingSpotRepository.getLotBaseRate(parkingSpotId);

        // Get the duration of the reservation in hours
        Timestamp startTimeStamp = Timestamp.valueOf(startTime);
        Timestamp endTimeStamp = Timestamp.valueOf(endTime);
        int durationInHours = (int) ((endTimeStamp.getTime() - startTimeStamp.getTime()) / (1000 * 60 * 60));

        // Calculate the total cost of the reservation
        int totalCost = baseRate * durationInHours;

        // Save the reservation to the database
        reservationRepository.saveReservation(parkingSpotId, driverId, startTime, endTime, totalCost);
    }
}
