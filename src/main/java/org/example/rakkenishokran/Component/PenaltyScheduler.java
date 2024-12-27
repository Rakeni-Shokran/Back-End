package org.example.rakkenishokran.Component;

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

    @Scheduled(fixedRate = 3000) // Runs every 30 seconds
    public void applyPenalties() {

        String query = "INSERT INTO PENALTY (fees, type, userId) " +
                "SELECT 50 AS fees, 'NotShowingUp' AS type, R.userId " +
                "FROM RESERVATION R " +
                "LEFT JOIN LOG_TABLE L ON R.id = L.reservationId " +
                "WHERE R.endTimeStamp < CURRENT_TIMESTAMP " +
                "AND L.reservationId IS NULL " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 FROM PENALTY P " +
                "    WHERE P.userId = R.userId AND P.type = 'NotShowingUp'" +
                ");";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Penalties applied: " + rowsAffected);
        } catch (Exception e) {
            System.err.println("Error executing penalty query: " + e.getMessage());
        }
    }
}
