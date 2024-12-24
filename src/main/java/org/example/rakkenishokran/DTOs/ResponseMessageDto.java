package org.example.rakkenishokran.DTOs;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ResponseMessageDTO {
    private String message;
    private boolean success;
    private Integer statusCode;
    private Object data;

}
