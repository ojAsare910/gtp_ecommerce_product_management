import {Component, OnInit} from "@angular/core";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from '../category.service';
import { Category } from '../category.model';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent implements OnInit {
  categoryForm: FormGroup;
  isEditMode = false;
  categoryId: number | null = null;  // Initialize with null

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null) {
      this.categoryId = +idParam;
      this.isEditMode = true;
      this.loadCategory();
    }
  }

  loadCategory(): void {
    if (this.categoryId !== null) {
      this.categoryService.getCategoryById(this.categoryId).subscribe(
        category => {
          this.categoryForm.patchValue(category);
        },
        error => console.error('Error loading category', error)
      );
    }
  }

  onSubmit(): void {
    if (this.categoryForm.valid) {
      const category: Category = this.categoryForm.value;
      if (this.isEditMode && this.categoryId !== null) {
        this.categoryService.updateCategory(this.categoryId, category).subscribe(
          () => this.router.navigate(['/categories']),
          error => console.error('Error updating category', error)
        );
      } else {
        this.categoryService.createCategory(category).subscribe(
          () => this.router.navigate(['/categories']),
          error => console.error('Error creating category', error)
        );
      }
    }
  }
}
