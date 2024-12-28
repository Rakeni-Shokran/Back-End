package org.example.rakkenishokran.Controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.DTOs.ParkingRequestDTO;
import org.example.rakkenishokran.DTOs.ReservationRequestDTO;
import org.example.rakkenishokran.Repositories.ParkingSpotRepository;
import org.example.rakkenishokran.Services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor //lombok annotation to create a constructor with all the required fields
@Getter

@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/getAllLots")
    public ResponseEntity<Object> getAllLots() {
        return reservationService.getAllLots();
    }

    @PostMapping("/getAvailableSpots")
    public ResponseEntity<Object> getAvailableSpots(@RequestBody ParkingRequestDTO request) {
//        System.out.println("request = " + request);
        return reservationService.getAvailableSpots(request);
//        System.out.println(parkingSpotRepository.findFreeParkingSpotsInTimeStampRange(location, startTime, startTime).toString());
    }

    @PostMapping("/reserveSpot")
    public ResponseEntity<Object> reserveSpot(@RequestBody ReservationRequestDTO request) {
        return reservationService.reserveParkingSpot(request);
    }

    @GetMapping("/getReservations/{driverId}")
    public ResponseEntity<Object> getReservations(@PathVariable long driverId) {
        try {
            return ResponseEntity.ok(reservationService.findAllByDriverIdFromNow(driverId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
