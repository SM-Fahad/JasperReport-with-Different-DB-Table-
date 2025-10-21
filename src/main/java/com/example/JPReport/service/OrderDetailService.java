package com.example.JPReport.service;// OrderDetailService.java

import com.example.JPReport.dtos.OrderDetailDTO;
import com.example.JPReport.repos.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetailDTO> getAllOrderDetails() {
        return orderDetailRepository.findAllOrderDetails();
    }

    public List<OrderDetailDTO> getOrderDetailsByCustomer(String customerName) {
        return orderDetailRepository.findOrderDetailsByCustomerName(customerName);
    }

    public List<OrderDetailDTO> getOrderDetailsByStatus(String status) {
        return orderDetailRepository.findOrderDetailsByStatus(status);
    }
}