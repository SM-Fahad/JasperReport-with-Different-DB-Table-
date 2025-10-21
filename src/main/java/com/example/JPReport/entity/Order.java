package com.example.JPReport.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "orderNumber")
    private Integer orderNumber;

    // Change from Date to LocalDate for DATE columns
    @Column(name = "orderDate")
    private LocalDate orderDate;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "customerNumber")
    private Customer customer;

    // Constructors, getters, setters
    public Order() {}

    public Order(Integer orderNumber, LocalDate orderDate, String status, Customer customer) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.status = status;
        this.customer = customer;
    }

    // Getters and setters
    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}