package com.example.JPReport.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDetailDTO {
    private String customerName;
    private Integer quantityOrdered;
    private BigDecimal priceEach;
    private BigDecimal total;
    private String status;
    private LocalDate orderDate;
    private Integer orderNumber;
    private String productName;

    // Constructor for native query result
    public OrderDetailDTO(String customerName, Integer quantityOrdered, BigDecimal priceEach,
                          BigDecimal total, String status, LocalDate orderDate,
                          Integer orderNumber, String productName) {
        this.customerName = customerName;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
        this.total = total;
        this.status = status;
        this.orderDate = orderDate;
        this.orderNumber = orderNumber;
        this.productName = productName;
    }

    // Getters and setters
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public Integer getQuantityOrdered() { return quantityOrdered; }
    public void setQuantityOrdered(Integer quantityOrdered) { this.quantityOrdered = quantityOrdered; }
    public BigDecimal getPriceEach() { return priceEach; }
    public void setPriceEach(BigDecimal priceEach) { this.priceEach = priceEach; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "customerName='" + customerName + '\'' +
                ", quantityOrdered=" + quantityOrdered +
                ", priceEach=" + priceEach +
                ", total=" + total +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                ", orderNumber=" + orderNumber +
                ", productName='" + productName + '\'' +
                '}';
    }
}