package org.example.rakkenishokran.Controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.Repositories.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor //lombok annotation to create a constructor with all the required fields
@Getter

@CrossOrigin
public class ReservationController {
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @GetMapping("/getAvailableSpots")
    public void getAvailableSpots() {
        System.out.println(parkingSpotRepository.findFreeParkingSpotsInTimeStampRange("123 Main St", "2023-10-02 11:00:00", "2023-10-02 12:00:00").toString());
    }

}
