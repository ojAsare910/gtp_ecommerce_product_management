package com.justiceasare.gtpproductmanagement.repository;

import com.justiceasare.gtpproductmanagement.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Boolean existsProductByNameIgnoreCase(String productName);

    Boolean existsProductByPrice(BigDecimal price);

    Boolean existsProductByDescription(String description);

    Boolean existsProductByCategoryId(Long category_id);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);
}
