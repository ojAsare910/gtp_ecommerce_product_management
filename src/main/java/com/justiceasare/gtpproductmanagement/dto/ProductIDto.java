package com.justiceasare.gtpproductmanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ProductIDto {
    Long getProductId();
    String getProductName();
    String getProductDescription();
    BigDecimal getProductPrice();
    LocalDate getProductCreationDate();
    Long getCategoryId();
    String getCategoryName();
}
