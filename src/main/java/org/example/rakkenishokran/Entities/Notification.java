package org.example.rakkenishokran.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Notification {
    private long id;
    private String messageType;
    private boolean seen;
    private Timestamp notificationTimeStamp;
    private long userId;
}
