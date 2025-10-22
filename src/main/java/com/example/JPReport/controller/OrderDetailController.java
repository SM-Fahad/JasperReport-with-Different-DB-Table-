package com.example.JPReport.controller;

import com.example.JPReport.dtos.OrderDetailDTO;
import com.example.JPReport.service.OrderDetailService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order-details")
@CrossOrigin(origins = "*")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    // Your existing endpoints
    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        try {
            List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByCustomer(
            @PathVariable String customerName) {
        try {
            List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetailsByCustomer(customerName);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByStatus(
            @PathVariable String status) {
        try {
            List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetailsByStatus(status);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // PDF Export for Order Details using your template
    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadOrderDetailsPdf() {
        try {
            // Get actual order details from service
            List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();

            if (orderDetails.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Create data source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderDetails);

            // Load your existing JRXML template from classpath
            InputStream templateStream = new ClassPathResource("Blank_A4.jrxml").getInputStream();

            // Compile report
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            // Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            // Export to PDF
            byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=order-details-report.pdf");

            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Multi-format export using your template
    @GetMapping(value = "/download/{format}")
    public ResponseEntity<byte[]> downloadOrderDetailsReport(@PathVariable String format) {
        try {
            // Get actual order details from service
            List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();

            if (orderDetails.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Create data source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderDetails);

            // Load your existing JRXML template from classpath
            InputStream templateStream = new ClassPathResource("Blank_A4.jrxml").getInputStream();

            // Compile report
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            // Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            byte[] data;
            HttpHeaders headers = new HttpHeaders();

            switch (format.toLowerCase()) {
                case "pdf":
                    data = JasperExportManager.exportReportToPdf(jasperPrint);
                    headers.setContentType(MediaType.APPLICATION_PDF);
                    headers.add("Content-Disposition", "inline; filename=order-details-report.pdf");
                    break;

                case "html":
                    data = exportToHtml(jasperPrint);
                    headers.setContentType(MediaType.TEXT_HTML);
                    headers.add("Content-Disposition", "inline; filename=order-details-report.html");
                    break;

                case "xlsx":
                    data = exportToXlsx(jasperPrint);
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    headers.add("Content-Disposition", "attachment; filename=order-details-report.xlsx");
                    break;

                case "csv":
                    data = exportToCsv(jasperPrint);
                    headers.setContentType(MediaType.TEXT_PLAIN);
                    headers.add("Content-Disposition", "attachment; filename=order-details-report.csv");
                    break;

                case "docx":
                    data = exportToDocx(jasperPrint);
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    headers.add("Content-Disposition", "attachment; filename=order-details-report.docx");
                    break;
                default:
                    return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok().headers(headers).body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Filtered PDF export by customer using your template
    @GetMapping(value = "/customer/{customerName}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadOrderDetailsByCustomerPdf(@PathVariable String customerName) {
        try {
            List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetailsByCustomer(customerName);

            if (orderDetails.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderDetails);

            InputStream templateStream = new ClassPathResource("Blank_A4.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=order-details-" + sanitizeFilename(customerName) + ".pdf");

            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Filtered PDF export by status using your template
    @GetMapping(value = "/status/{status}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadOrderDetailsByStatusPdf(@PathVariable String status) {
        try {
            List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetailsByStatus(status);

            if (orderDetails.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderDetails);

            InputStream templateStream = new ClassPathResource("Blank_A4.jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=order-details-" + sanitizeFilename(status) + ".pdf");

            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // Helper methods for different export formats
    private byte[] exportToHtml(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));

        // Optional: Configure HTML export settings
        SimpleHtmlExporterConfiguration configuration = new SimpleHtmlExporterConfiguration();
        configuration.setBetweenPagesHtml("");
        exporter.setConfiguration(configuration);

        exporter.exportReport();
        return outputStream.toByteArray();
    }

    private byte[] exportToXlsx(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        configuration.setRemoveEmptySpaceBetweenColumns(true);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        exporter.setConfiguration(configuration);

        exporter.exportReport();
        return outputStream.toByteArray();
    }

    private byte[] exportToCsv(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));

        SimpleCsvExporterConfiguration csvConfig = new SimpleCsvExporterConfiguration();
        csvConfig.setFieldDelimiter(",");
        exporter.setConfiguration(csvConfig);

        exporter.exportReport();
        return outputStream.toByteArray();
    }

    private byte[] exportToDocx(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();
        return outputStream.toByteArray();
    }

    // Helper method to sanitize filenames
    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }
}