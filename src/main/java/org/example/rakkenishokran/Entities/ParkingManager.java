package org.example.rakkenishokran.Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ParkingManager extends User{
    public ParkingManager(long id, String username, String password, String email, String phoneNumber) {
        super(id, email, username, password, phoneNumber);
    }
}
