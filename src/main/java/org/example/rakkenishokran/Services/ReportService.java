package org.example.rakkenishokran.Services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.rakkenishokran.Entities.PenaltyReport;
import org.example.rakkenishokran.Entities.ParkingLotReport;
import org.example.rakkenishokran.Repositories.PenaltyRepository;
import org.example.rakkenishokran.Repositories.ParkingLotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private PenaltyRepository notificationRepository;

//    public ReportService(NotificationRepository notificationRepository) {
//        this.notificationRepository = notificationRepository;
//    }
    @Autowired
    private  ParkingLotsRepository parkingLotsRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public byte[] generateParkingLotReport() {
        List<ParkingLotReport> parkingLotReports = parkingLotsRepository.getParkingLotReports();

        try {
            // Load Jasper template
            InputStream templateStream = getClass().getResourceAsStream("/parking_lot_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            // Convert data to JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(parkingLotReports);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("reportTitle", "Parking Lot Occupancy and Revenue Report");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export the report to a PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RuntimeException("Error generating report: " + e.getMessage(), e);
        }
    }

    public byte[] generateNotificationsReport() {
        List<PenaltyReport> notifications = notificationRepository.getAllNotifications();

        // Pass the data to JasperReports
        try {
            // Load Jasper template
            InputStream templateStream = getClass().getResourceAsStream("/notifications_report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            // Convert data to JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(notifications);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("reportTitle", "Top Parking Lot Revenues");
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export the report to a PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RuntimeException("Error generating report: " + e.getMessage(), e);
        }
    }
}
