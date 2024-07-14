import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product.model';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  currentPage = 0;
  pageSize = 10;
  totalItems = 0;
  searchTerm: string = '';

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts(this.currentPage, this.pageSize, this.searchTerm).subscribe(
      response => {
        this.products = response.content;
        this.totalItems = response.totalElements;
      },
      error => console.error('Error loading products', error)
    );
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadProducts();
  }

  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe(
      () => {
        this.loadProducts();
      },
      error => console.error('Error deleting product', error)
    );
  }

  onSearch(): void {
    this.loadProducts(); // Reload products based on new search term
  }
}
