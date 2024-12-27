package org.example.rakkenishokran.Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class ParkingManager extends User{
    private boolean isApproved;
    public ParkingManager(long id, String username, String password, String email, String phoneNumber) {
        super(id, email, username, password, phoneNumber);
        this.isApproved = false;
    }

}
