package com.example.JPReport.controller;

import com.example.JPReport.dtos.OrderDetailDTO;
import com.example.JPReport.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
@CrossOrigin(origins = "*")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        try {
            List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}