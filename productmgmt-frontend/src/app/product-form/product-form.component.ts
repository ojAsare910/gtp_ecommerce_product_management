import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../product.service';
import { CategoryService } from '../category.service';
import { Product } from '../product.model';
import { Category } from '../category.model';
import {ProductDao} from "../productdao.model";

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {
  productForm: FormGroup;
  isEditMode = false;
  productId: number | null = null;
  categories: Category[] = [];

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.productForm = this.fb.group({
      productName: ['', Validators.required],
      productPrice: ['', Validators.required],
      productDescription: ['', Validators.required],
      categoryId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadCategories();
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null) {
      this.productId = +idParam;
      this.isEditMode = true;
      this.loadProduct();
    }
  }

  loadCategories(): void {
    this.categoryService.getCategories(0, 1000).subscribe(
      response => {
        this.categories = response.content;
      },
      error => console.error('Error loading categories', error)
    );
  }

  loadProduct(): void {
    if (this.productId !== null) {
      this.productService.getProductById(this.productId).subscribe(
        (product: Product) => {
          this.productForm.patchValue({
            productName: product.productName,
            productPrice: product.productPrice,
            productDescription: product.productDescription,
            categoryId: product.categoryId
          });
        },
        error => console.error('Error loading product', error)
      );
    }
  }

  onSubmit(): void {
    if (this.productForm.valid) {
      const productDao: ProductDao = {
        productName: this.productForm.get('productName')?.value || '',
        productPrice: this.productForm.get('productPrice')?.value || 0,
        productDescription: this.productForm.get('productDescription')?.value || '',
        categoryId: this.productForm.get('categoryId')?.value || 0
      };

      if (this.isEditMode && this.productId !== null) {
        productDao.productId = this.productId;
        this.productService.updateProduct(this.productId, productDao).subscribe(
          () => this.router.navigate(['/products']),
          error => {
            console.error('Error updating product', error);
            // You might want to show an error message to the user here
          }
        );
      } else {
        this.productService.createProduct(productDao).subscribe(
          () => this.router.navigate(['/products']),
          error => {
            console.error('Error creating product', error);
            // You might want to show an error message to the user here
          }
        );
      }
    }
  }
}
