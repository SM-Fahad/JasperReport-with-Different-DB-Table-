package com.example.JPReport.repos;

import com.example.JPReport.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ProductRepository.java
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}