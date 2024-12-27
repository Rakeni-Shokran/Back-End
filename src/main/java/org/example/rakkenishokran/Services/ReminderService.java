package org.example.rakkenishokran.Services;

import org.example.rakkenishokran.DTOs.MailBodyDTO;
import org.example.rakkenishokran.Entities.Reservation;
import org.example.rakkenishokran.Entities.User;
import org.example.rakkenishokran.Repositories.DriverRepository;
import org.example.rakkenishokran.Repositories.ReservationRepository;
import org.example.rakkenishokran.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final EmailService emailService;
    private final DriverRepository driverRepository;
    private final ReservationRepository reservationRepository;

    public void sendReminderReservationNearToFinish() {
        // Fetch all drivers
        List<User> drivers = driverRepository.findAll();
        System.out.println("Drivers: " + drivers);

        if (drivers == null || drivers.isEmpty()) {
            System.out.println("No drivers found.");
            return;
        }

        for (User driver : drivers) {
            List<Reservation> reservations = reservationRepository.findAllByDriverId(driver.getId());

            for (Reservation reservation : reservations) {
                try {

                    // Parse the end timestamp into LocalDateTime
                    Timestamp endTimestamp = reservation.getEndTimeStamp();
                    if (endTimestamp == null) {
                        continue;
                    }

                    LocalDateTime reservationEndDateTime = endTimestamp.toLocalDateTime();

                    // Check if the reservation is within 5 minutes of ending and not already reminded
                    if (!reservation.isReminded() &&
                            LocalDateTime.now().plusMinutes(5).isAfter(reservationEndDateTime) &&
                            LocalDateTime.now().isBefore(reservationEndDateTime)) {

                        reservation.setReminded(true);


                        MailBodyDTO mailBody = MailBodyDTO.builder()
                                .to(driver.getEmail())
                                .subject("Reminder: Your reservation is about to finish")
                                .body(
                                        "<html>" +
                                                "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333333;'>" +
                                                "<p>Dear " + driver.getUsername() + ",</p>" +
                                                "<p>This is a friendly reminder that your parking reservation is about to finish <strong>after 5 minutes</strong> at: </p>" +
                                                "<p style='font-size: 18px; font-weight: bold; color: #2b8cbe;'>" +
                                                reservationEndDateTime.format(DateTimeFormatter.ofPattern("HH:mm")) +
                                                "</p>" +
                                                "<p>Please make sure to arrive on time and take your car 👋👋.</p>" +
                                                "<p>If you have any questions or need further assistance, feel free to contact our team.</p>" +
                                                "<p>Best regards,<br><strong>Rakkeni Shokran Team</strong></p>" +
                                                "</body>" +
                                                "</html>"
                                )
                                .build();

                        // Send the reminder email
                        emailService.sendHtmlMessage(mailBody);

                        // Update the reservation in the database
                        reservationRepository.updateReservation(
                                reservation.getStartTimeStamp().toString(),
                                reservation.getEndTimeStamp().toString(),
                                reservation.getParkingSpotId(),
                                true
                        );
                    }
                } catch (EmptyResultDataAccessException e) {
                    System.out.println("No reservations found for driver ID: " + driver.getId());
                } catch (Exception e) {
                    // Handle any unexpected exceptions
                    System.err.println("Error processing reservation ID: " + reservation.getId() +
                            ", Error: " + e.getMessage());
                }
            }
        }
    }
}
