package org.example.rakkenishokran.Services;

import lombok.AllArgsConstructor;
import org.example.rakkenishokran.DTOs.ParkingLotNamesDTO;
import org.example.rakkenishokran.Entities.ParkingLot;
import org.example.rakkenishokran.Repositories.ParkingLotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParkingLotService {
    @Autowired
    private ParkingLotsRepository parkingLotsRepository;

    public List<ParkingLotNamesDTO> findAllParkingLotsNames() {
        List<ParkingLot> list =  parkingLotsRepository.findAllParkingLotsNames();
        System.out.println("listaaaaaaa = " + list);
        return list.stream()
                .map(parkingLot -> ParkingLotNamesDTO.builder()
                        .id(parkingLot.getId())
                        .name(parkingLot.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
