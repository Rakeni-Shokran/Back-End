package org.example.rakkenishokran.Component;

import org.example.rakkenishokran.Services.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class PenaltyScheduler {

    @Autowired
    private DataSource dataSource;

    @Scheduled(fixedRate = 3000) // Runs every 3 seconds
    public void applyPenalties() {

        String query = "CALL handle_no_show_penalties()";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Penalties applied: " + rowsAffected);
        } catch (Exception e) {
            System.err.println("Error executing penalty query: " + e.getMessage());
        }
    }
}
