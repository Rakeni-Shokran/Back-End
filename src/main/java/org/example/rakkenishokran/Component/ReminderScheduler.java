
package org.example.rakkenishokran.Component;


import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.Services.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {

    private final NotificationService notificationService;
    @Scheduled(fixedRate = 30000) // 30 seconds
    public void sendReminder() {
        System.out.println("Sending reminder");
        notificationService.sendReminderReservationNearToFinish();
    }

}
