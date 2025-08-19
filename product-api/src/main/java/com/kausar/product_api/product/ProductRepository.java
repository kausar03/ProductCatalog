package com.kausar.product_api.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String q);
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
