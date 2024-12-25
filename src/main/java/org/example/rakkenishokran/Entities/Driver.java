package org.example.rakkenishokran.Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Driver extends User{

    private String paymentMethod;
    private String licenseNumber;
    private Integer userID;

}
