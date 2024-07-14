package com.justiceasare.gtpproductmanagement.repository;

import com.justiceasare.gtpproductmanagement.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Boolean existsProductByNameIgnoreCase(String productName);

    Boolean existsProductByPrice(BigDecimal price);

    Boolean existsProductByDescription(String description);

    Boolean existsProductByCategoryId(Long category_id);

    @Query(value = "Select * from product p where p.name = %:productName", nativeQuery = true)
    Product getProductByNameIgnoreCase(String productName);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);
}
