package org.example.rakkenishokran.Services;
import org.example.rakkenishokran.DTOs.*;
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
            // Create a MimeMessage
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Set email details
            helper.setTo(mailBody.to());
            helper.setFrom("medcaresystem07@gmail.com");
            helper.setSubject(mailBody.subject());

            // Set the email content as HTML
            helper.setText(mailBody.body(), true);

            // Send the email
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }


}
