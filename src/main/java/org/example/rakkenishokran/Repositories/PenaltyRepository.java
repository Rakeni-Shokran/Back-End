package org.example.rakkenishokran.Repositories;

import org.example.rakkenishokran.Entities.PenaltyReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PenaltyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<PenaltyReport> getAllNotifications() {
        String sql = "SELECT u.id AS userId, u.name AS username, u.email, u.phoneNumber AS phone, " +
                "n.messageType AS messageType, n.notificationTimeStamp AS notificationTimeStamp " +
                "FROM NOTIFICATION n JOIN USER u ON n.userId = u.id";
        List<PenaltyReport> notifications = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new PenaltyReport(
                        rs.getLong("userId"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("messageType"),
                        rs.getTimestamp("notificationTimeStamp")
                )
        );

        return notifications;
    }
}
