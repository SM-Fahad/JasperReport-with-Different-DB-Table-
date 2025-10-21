package com.example.JPReport.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orderdetails")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;


    @ManyToOne
    @MapsId("orderNumber")
    @JoinColumn(name = "orderNumber")
    private Order order;

    @ManyToOne
    @MapsId("productCode")
    @JoinColumn(name = "productCode")
    private Product product;

    @Column(name = "quantityOrdered")
    private Integer quantityOrdered;

    @Column(name = "priceEach", precision = 10, scale = 2)
    private BigDecimal priceEach;

    // Constructors, getters, setters
    public OrderDetail() {}

    public OrderDetail(OrderDetailId id, Order order, Product product,
                       Integer quantityOrdered, BigDecimal priceEach) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
    }

    // Getters and setters
    public OrderDetailId getId() { return id; }
    public void setId(OrderDetailId id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantityOrdered() { return quantityOrdered; }
    public void setQuantityOrdered(Integer quantityOrdered) { this.quantityOrdered = quantityOrdered; }
    public BigDecimal getPriceEach() { return priceEach; }
    public void setPriceEach(BigDecimal priceEach) { this.priceEach = priceEach; }
}