package org.example.rakkenishokran.Services;

import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.DTOs.*;
import org.example.rakkenishokran.Entities.ParkingSpot;
import org.example.rakkenishokran.Entities.User;
import org.example.rakkenishokran.Repositories.ParkingSpotRepository;
import org.example.rakkenishokran.Repositories.ReservationRepository;
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
    private final ReservationRepository reservationRepository;

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


            long totalCost = baseRate * durationInHours +1;

            // Save the reservation to the database
            reservationRepository.saveReservation(parkingSpotId, driverId, startTime, endTime, totalCost, false);

            return ResponseEntity.ok().body(ResponseMessageDTO.builder().success(true)
                    .message("Parking spot reserved successfully").statusCode(200).build());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ResponseMessageDTO.builder().success(false)
                    .message("An unexpected error occurred").statusCode(500).build());
        }


    }
}
