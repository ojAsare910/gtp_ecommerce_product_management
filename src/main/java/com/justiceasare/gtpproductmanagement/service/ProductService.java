package com.justiceasare.gtpproductmanagement.service;

import com.justiceasare.gtpproductmanagement.dto.ProductDto;
import com.justiceasare.gtpproductmanagement.dto.ProductIDto;
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
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Page<ProductIDto> getProducts(int page, int size, String searchTerm) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());

    if (searchTerm != null && !searchTerm.isEmpty()) {
        return productRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
    } else {
        return productRepository.findAllByDto(pageable);
    }
}

    public Optional<ProductIDto> getProduct(Long id) {
        return Optional.ofNullable(productRepository.findProductByIdWithDto(id));
    }

    public Product createProduct(ProductDto productDto) {
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        if (productDto.getProductName().isEmpty() || productDto.getProductDescription().isEmpty()) {
            throw new IllegalArgumentException("These fields cannot be empty!");
        } else if (
                productRepository.existsProductByCategoryId(productDto.getCategoryId())
                && productRepository.existsProductByPrice(productDto.getProductPrice())
                && productRepository.existsProductByDescription(productDto.getProductDescription())
                && productRepository.existsProductByNameIgnoreCase(productDto.getProductName())
        ) {
            throw new IllegalArgumentException(
                    "Product: " + productDto.getProductName() + " already exists with the exact details!");
        }

        Product product = Product.builder()
                .name(productDto.getProductName())
                .price(productDto.getProductPrice())
                .description(productDto.getProductDescription())
                .category(category).build();
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, ProductDto productDto) {
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        if (productDto.getProductName().isEmpty()) {
            throw new IllegalArgumentException("These fields cannot be empty!");
        } else if (
                productRepository.existsProductByCategoryId(productDto.getCategoryId())
                        && productRepository.existsProductByPrice(productDto.getProductPrice())
                        && productRepository.existsProductByDescription(productDto.getProductDescription())
                        && productRepository.existsProductByNameIgnoreCase(productDto.getProductName())
        ) {
            throw new IllegalArgumentException(
                    "Product: " + productDto.getProductName() + " already exists with the exact details!");
        }
        return productRepository.findById(productId).map(product -> {
            product.setName(productDto.getProductName());
            product.setPrice(productDto.getProductPrice());
            product.setDescription(productDto.getProductDescription());
            product.setCategory(category);
            return productRepository.save(product);
        }).orElseThrow(() -> new NotFoundException("Product with id: [%s] not found!]".formatted(productId)));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}