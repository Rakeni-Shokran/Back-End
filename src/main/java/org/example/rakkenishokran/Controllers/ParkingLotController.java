package org.example.rakkenishokran.Controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.Services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkingLot")
@RequiredArgsConstructor //lombok annotation to create a constructor with all the required fields
@Getter

@CrossOrigin
public class ParkingLotController {
    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping("/getAll")
    public ResponseEntity<Object> findAllParkingLotsNames() {
        try{
            return ResponseEntity.ok(parkingLotService.findAllParkingLotsNames());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
