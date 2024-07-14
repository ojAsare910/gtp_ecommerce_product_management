package com.justiceasare.gtpproductmanagement.repository;

import com.justiceasare.gtpproductmanagement.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsCategoryByNameIgnoreCase(String name);

}
