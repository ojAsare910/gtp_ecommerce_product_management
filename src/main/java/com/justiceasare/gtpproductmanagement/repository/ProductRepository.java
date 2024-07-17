package com.justiceasare.gtpproductmanagement.repository;

import com.justiceasare.gtpproductmanagement.dto.ProductDto;
import com.justiceasare.gtpproductmanagement.dto.ProductIDto;
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

    @Query(value = "select p.id as productId, p.name as productName, p.description as productDescription,\n" +
            "    p.price as productPrice, p.created_at as productCreationDate, p.category_id as categoryId,\n" +
            "    c.name as categoryName from Product p left join Category c on p.category_id = c.id", nativeQuery = true)
    Page<ProductIDto> findAllByDto(Pageable pageable);

    Page<ProductIDto> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);

    @Query(value = "select p.id as productId, p.name as productName, p.description as productDescription,\n" +
            "    p.price as productPrice, p.created_at as productCreationDate, p.category_id as categoryId,\n" +
            "    c.name as categoryName from Product p left join Category c on p.category_id = c.id where p.id = :productId;", nativeQuery = true)
    ProductIDto findProductByIdWithDto(Long productId);
}
