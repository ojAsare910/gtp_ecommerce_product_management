import { Component } from '@angular/core';
import { Category } from '../category.model';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent {
  categories: Category[] = [];
  currentPage = 0;
  pageSize = 10;
  totalItems = 0;

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getCategories(this.currentPage, this.pageSize).subscribe(
      response => {
        this.categories = response.content;
        this.totalItems = response.totalElements;
      },
      error => console.error('Error loading categories', error)
    );
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadCategories();
  }

  deleteCategory(id: number): void {
    this.categoryService.deleteCategory(id).subscribe(
      () => {
        this.loadCategories();
      },
      error => console.error('Error deleting category', error)
    );
  }

}
