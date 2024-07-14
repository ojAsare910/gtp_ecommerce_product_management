import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../product.service';
import { CategoryService } from '../category.service';
import { Product } from '../product.model';
import { Category } from '../category.model';

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
      name: ['', Validators.required],
      price: ['', Validators.required],
      description: ['', Validators.required],
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
        product => {
          this.productForm.patchValue(product);
        },
        error => console.error('Error loading product', error)
      );
    }
  }

  onSubmit(): void {
    if (this.productForm.valid) {
      const product: Product = this.productForm.value;
      if (this.isEditMode && this.productId !== null) {
        this.productService.updateProduct(this.productId, product).subscribe(
          () => this.router.navigate(['/products']),
          error => console.error('Error updating product', error)
        );
      } else {
        this.productService.createProduct(product).subscribe(
          () => this.router.navigate(['/products']),
          error => console.error('Error creating product', error)
        );
      }
    }
  }
}
