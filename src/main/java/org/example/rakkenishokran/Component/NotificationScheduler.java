package org.example.rakkenishokran.Component;

import org.example.rakkenishokran.DTOs.MailBodyDTO;
import org.example.rakkenishokran.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

@Component
public class NotificationScheduler {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedRate = 3000) // Runs every 3 seconds
    public void sendNotifications() {
        String fetchNotificationsQuery = """
            SELECT N.id, N.messageType, U.email
            FROM NOTIFICATION N
            INNER JOIN USER U ON N.userId = U.id
            WHERE N.seen = 0;
        """;

        String updateNotificationQuery = "UPDATE NOTIFICATION SET seen = 1 WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement fetchStatement = connection.prepareStatement(fetchNotificationsQuery);
             ResultSet resultSet = fetchStatement.executeQuery()) {

            while (resultSet.next()) {
                long notificationId = resultSet.getLong("id");
                String messageType = resultSet.getString("messageType");
                String userEmail = resultSet.getString("email");

                // Send email notification
                MailBodyDTO mailBody = MailBodyDTO.builder()
                        .to(userEmail)
                        .subject("Notification")
                        .body(messageType)
                        .build();

                try {
                    emailService.sendHtmlMessage(mailBody);
                    System.out.println("Notification sent to: " + userEmail);

                    // Mark the notification as seen
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateNotificationQuery)) {
                        updateStatement.setLong(1, notificationId);
                        updateStatement.executeUpdate();
                    }
                } catch (Exception e) {
                    System.err.println("Failed to send email to: " + userEmail);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching notifications: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
