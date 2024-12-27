package org.example.rakkenishokran.Component;


//import org.example.rakkenishokran.Services.ReminderService;
import lombok.RequiredArgsConstructor;
import org.example.rakkenishokran.Services.ReminderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {

    private final ReminderService reminderService;
    @Scheduled(fixedRate = 30000) // 30 seconds
    public void sendReminder() {
        System.out.println("Sending reminder");
        reminderService.sendReminderReservationNearToFinish();
    }

}
