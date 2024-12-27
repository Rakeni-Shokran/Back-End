package org.example.rakkenishokran.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ParkingRequestDTO {
    private String location;
    private String startTime;
    private String endTime;
}
