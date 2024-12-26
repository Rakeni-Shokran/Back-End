package org.example.rakkenishokran.Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SysAdmin extends User {
    public SysAdmin(long id, String username, String password, String email, String phoneNumber) {
        super(id, email, username, password, phoneNumber);
    }
}
