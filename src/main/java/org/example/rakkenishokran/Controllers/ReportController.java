package org.example.rakkenishokran.Controllers;

import org.example.rakkenishokran.Entities.ParkingLotReport;
import org.example.rakkenishokran.Services.ReportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/parking-lot-report")
    public ResponseEntity<byte[]> getParkingLotReport() {
        try {
            // Generate the parking lot report
            byte[] parkingLotReport = reportService.generateParkingLotReport();

            // Set headers for PDF response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("inline").filename("parking_lot_report.pdf").build());

            return new ResponseEntity<>(parkingLotReport, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            System.out.println("Error generating parking lot report: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/penalty-report")
    public ResponseEntity<byte[]> getNotificationsReport() {
        byte[] report = reportService.generateNotificationsReport();

        // Set headers for PDF response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("notifications_report.pdf").build());

        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }
}
