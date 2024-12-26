package org.example.rakkenishokran.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Penalty {
    private long id;
    private int fees;
    private String type;
    private long userId;
}
