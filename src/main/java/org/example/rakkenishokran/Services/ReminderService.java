package org.example.rakkenishokran.Services;


import org.example.rakkenishokran.DTOs.MailBodyDTO;
import org.example.rakkenishokran.Entities.Driver;
import org.example.rakkenishokran.Entities.Reservation;
import org.example.rakkenishokran.Repositories.DriverRepository;
import org.example.rakkenishokran.Repositories.ReservationRepository;
import org.example.rakkenishokran.Services.EmailService;
import org.example.rakkenishokran.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReminderService {


 private final EmailService emailService;
 private final UserRepository userRepository;
 private final ReservationRepository reservationRepository;
 private final DriverRepository driverRepository;


 public void sendReminderReservationNearToFinish() {
  // Define formatter for parsing date strings
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  // Fetch all patients

  List<Driver> drivers = driverRepository.findAll();
  if(drivers == null){
   return;
  }
  for (Driver driver : drivers) {
   List<Reservation> reservations = reservationRepository.findAllByDriver(driver);

   for (Reservation reservation : reservations) {
    try {
     // Parse appointmentTime into LocalDateTime
     Timestamp timestamp = reservation.getEndTimeStamp();


     LocalDateTime reservationEndDateTime = LocalDateTime.parse((CharSequence) timestamp, formatter);

     if (!reservation.isReminded() &&LocalDateTime.now().plusMinutes(5).toLocalDate().isEqual(reservationEndDateTime.toLocalDate())) {
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
      emailService.sendHtmlMessage(mailBody);
      reservationRepository.saveReservation(reservation.getParkingSpotId(), reservation.getUserId(), reservation.getStartTimeStamp().toString(), reservation.getEndTimeStamp().toString(), reservation.getPrice(), true);
     }

    } catch (Exception e) {
     // Handle invalid date format or null values
     System.err.println("Invalid date format for reservation: " + reservation.getEndTimeStamp());
    }
   }
  }
 }



}
