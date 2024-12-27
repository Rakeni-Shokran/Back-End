package org.example.rakkenishokran.Services;

import org.example.rakkenishokran.DTOs.MailBodyDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendHtmlMessage(MailBodyDTO mailBody) {
        try {
//            System.out.println("Sending email to: " + mailBody.to());

            // Create a MimeMessage
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Set email details
            helper.setTo(mailBody.to());
            helper.setFrom("rakkenishokran@gmail.com"); // Ensure this matches spring.mail.username
            helper.setSubject(mailBody.subject());
            helper.setText(mailBody.body(), true); // Set the email content as HTML

            // Send the email
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.err.println("Failed to send email to: " + mailBody.to());
            e.printStackTrace(); // Log the full stack trace for debugging
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
