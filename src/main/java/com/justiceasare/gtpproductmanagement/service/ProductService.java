package com.justiceasare.gtpproductmanagement.service;

import com.justiceasare.gtpproductmanagement.dto.ProductDto;
import com.justiceasare.gtpproductmanagement.exception.NotFoundException;
import com.justiceasare.gtpproductmanagement.model.Category;
import com.justiceasare.gtpproductmanagement.model.Product;
import com.justiceasare.gtpproductmanagement.repository.CategoryRepository;
import com.justiceasare.gtpproductmanagement.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    public Page<Product> getProducts(int page, int size, String searchTerm) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        if (searchTerm != null && !searchTerm.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }


    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(ProductDto productDto) {
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        if (productDto.getName().isEmpty() || productDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("These fields cannot be empty!");
        } else if (
                productRepository.existsProductByCategoryId(productDto.getCategoryId())
                && productRepository.existsProductByPrice(productDto.getPrice())
                && productRepository.existsProductByDescription(productDto.getDescription())
                && productRepository.existsProductByNameIgnoreCase(productDto.getName())
        ) {
            throw new IllegalArgumentException(
                    "Product: " + productDto.getName() + " already exists with the exact details!");
        }

        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .category(category).build();
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, ProductDto productDto) {
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        if (productDto.getName().isEmpty()) {
            throw new IllegalArgumentException("These fields cannot be empty!");
        } else if (
                productRepository.existsProductByCategoryId(productDto.getCategoryId())
                        && productRepository.existsProductByPrice(productDto.getPrice())
                        && productRepository.existsProductByDescription(productDto.getDescription())
                        && productRepository.existsProductByNameIgnoreCase(productDto.getName())
        ) {
            throw new IllegalArgumentException(
                    "Product: " + productDto.getName() + " already exists with the exact details!");
        }
        return productRepository.findById(productId).map(product -> {
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategory(category);
            return productRepository.save(product);
        }).orElseThrow(() -> new NotFoundException("Product with id: [%s] not found!]".formatted(productId)));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}