package org.example.rakkenishokran.Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Driver extends User{
    public Driver(long id, String username, String password, String email, String phoneNumber, String paymentMethod, String licenseNumber) {
        super(id, email, username, password, phoneNumber);
        this.paymentMethod = paymentMethod;
        this.licenseNumber = licenseNumber;
    }
    private String paymentMethod;
    private String licenseNumber;
}
