package com.example.JPReport.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Product.java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "productCode")
    private String productCode;

    @Column(name = "productName")
    private String productName;

    // Constructors, getters, setters
    public Product() {}

    public Product(String productCode, String productName) {
        this.productCode = productCode;
        this.productName = productName;
    }

    // Getters and setters
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
}
