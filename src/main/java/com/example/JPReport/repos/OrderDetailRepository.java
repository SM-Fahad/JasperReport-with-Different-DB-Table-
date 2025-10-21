package com.example.JPReport.repos;

import com.example.JPReport.dtos.OrderDetailDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDetailRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<OrderDetailDTO> findAllOrderDetails() {
        String sql = "SELECT " +
                "c.customerName, " +
                "od.quantityOrdered, " +
                "od.priceEach, " +
                "od.quantityOrdered * od.priceEach AS total, " +
                "o.status, " +
                "o.orderDate, " +
                "o.orderNumber, " +
                "p.productName " +
                "FROM orderdetails od " +
                "JOIN orders o ON od.orderNumber = o.orderNumber " +
                "JOIN products p ON od.productCode = p.productCode " +
                "JOIN customers c ON o.customerNumber = c.customerNumber";

        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();
        return convertToDTO(results);
    }

    public List<OrderDetailDTO> findOrderDetailsByCustomerName(String customerName) {
        String sql = "SELECT " +
                "c.customerName, " +
                "od.quantityOrdered, " +
                "od.priceEach, " +
                "od.quantityOrdered * od.priceEach AS total, " +
                "o.status, " +
                "o.orderDate, " +
                "o.orderNumber, " +
                "p.productName " +
                "FROM orderdetails od " +
                "JOIN orders o ON od.orderNumber = o.orderNumber " +
                "JOIN products p ON od.productCode = p.productCode " +
                "JOIN customers c ON o.customerNumber = c.customerNumber " +
                "WHERE c.customerName LIKE :customerName";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("customerName", "%" + customerName + "%");
        List<Object[]> results = query.getResultList();
        return convertToDTO(results);
    }

    public List<OrderDetailDTO> findOrderDetailsByStatus(String status) {
        String sql = "SELECT " +
                "c.customerName, " +
                "od.quantityOrdered, " +
                "od.priceEach, " +
                "od.quantityOrdered * od.priceEach AS total, " +
                "o.status, " +
                "o.orderDate, " +
                "o.orderNumber, " +
                "p.productName " +
                "FROM orderdetails od " +
                "JOIN orders o ON od.orderNumber = o.orderNumber " +
                "JOIN products p ON od.productCode = p.productCode " +
                "JOIN customers c ON o.customerNumber = c.customerNumber " +
                "WHERE o.status = :status";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("status", status);
        List<Object[]> results = query.getResultList();
        return convertToDTO(results);
    }

    private List<OrderDetailDTO> convertToDTO(List<Object[]> results) {
        List<OrderDetailDTO> orderDetails = new ArrayList<>();

        for (Object[] result : results) {
            try {
                OrderDetailDTO dto = new OrderDetailDTO(
                        (String) result[0],                           // customerName
                        ((Number) result[1]).intValue(),              // quantityOrdered
                        (BigDecimal) result[2],                       // priceEach
                        (BigDecimal) result[3],                       // total
                        (String) result[4],                           // status
                        convertToLocalDate(result[5]),                // orderDate
                        ((Number) result[6]).intValue(),              // orderNumber
                        (String) result[7]                            // productName
                );
                orderDetails.add(dto);
            } catch (Exception e) {
                System.err.println("Error converting result to DTO: " + e.getMessage());
                // Continue processing other records
            }
        }

        return orderDetails;
    }

    private LocalDate convertToLocalDate(Object dateObject) {
        if (dateObject instanceof Date) {
            return ((Date) dateObject).toLocalDate();
        } else if (dateObject instanceof java.util.Date) {
            return new Date(((java.util.Date) dateObject).getTime()).toLocalDate();
        } else if (dateObject instanceof LocalDate) {
            return (LocalDate) dateObject;
        } else {
            throw new IllegalArgumentException("Unsupported date type: " + dateObject.getClass());
        }
    }
}