package org.example.rakkenishokran.DTOs;


import lombok.Builder;

@Builder
public record MailBodyDTO(String to, String subject, String body ) {
}
