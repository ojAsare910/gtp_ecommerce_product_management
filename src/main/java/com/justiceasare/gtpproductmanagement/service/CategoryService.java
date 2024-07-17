package com.justiceasare.gtpproductmanagement.service;

import com.justiceasare.gtpproductmanagement.exception.DuplicateResourceException;
import com.justiceasare.gtpproductmanagement.exception.NullResourceException;
import com.justiceasare.gtpproductmanagement.model.Category;
import com.justiceasare.gtpproductmanagement.model.CategoryNode;
import com.justiceasare.gtpproductmanagement.model.CategoryTree;
import com.justiceasare.gtpproductmanagement.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryTree categoryTree;

    public CategoryService(CategoryRepository categoryRepository, CategoryTree categoryTree) {
        this.categoryRepository = categoryRepository;
        this.categoryTree = categoryTree;
    }

    @Transactional
    public Category createCategory(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new NullResourceException(
                    "Category name must not be null or empty"
            );
        }
        if (categoryRepository.existsCategoryByNameIgnoreCase(name)) {
            throw new DuplicateResourceException(
                    "Category with name [%s] already exists".formatted(name)
            );
        }
        Category category = new Category();
        category.setName(name);
        category = categoryRepository.save(category);
        categoryTree.insert(category.getId(), category.getName());
        return category;
    }


    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalStateException("Invalid category id"));
    }

    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public void deleteCategory(String name) {
        CategoryNode node = categoryTree.search(name);
        if (node != null) {
            categoryRepository.deleteById(node.getId());
            categoryTree.delete(name);
        }
    }

    @Transactional
    public Category updateCategory(Long id, String newName) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (newName == null || newName.trim().isEmpty()) {
            throw new NullResourceException(
                    "Category name must not be null or empty"
            );
        }
        if (categoryRepository.existsCategoryByNameIgnoreCase(newName)) {
            throw new DuplicateResourceException(
                    "Category with name [%s] already exists".formatted(newName)
            );
        }
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            String oldName = category.getName();
            category.setName(newName);
            category = categoryRepository.save(category);
            categoryTree.delete(oldName);
            categoryTree.insert(category.getId(), newName);
            return category;
        }
        return null;
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id"));
        return categoryRepository.findAll(pageable);
    }
    
}
