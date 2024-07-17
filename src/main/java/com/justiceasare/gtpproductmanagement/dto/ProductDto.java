package com.justiceasare.gtpproductmanagement.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public class ProductDto {
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private LocalDate productCreationDate;
    private Long categoryId;
    private String categoryName;

    public ProductDto(Long productId, String productName, BigDecimal productPrice,
                      String productDescription, LocalDate productCreationDate,
                      Long categoryId, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productCreationDate = productCreationDate;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public ProductDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public LocalDate getProductCreationDate() {
        return productCreationDate;
    }

    public void setProductCreationDate(LocalDate productCreationDate) {
        this.productCreationDate = productCreationDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
