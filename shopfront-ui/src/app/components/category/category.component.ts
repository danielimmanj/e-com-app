import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Category } from '../../model/category.model';
import { CommonModule } from '@angular/common';
import { CategoryService } from '../../services/category/category.service';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
})
export class CategoryComponent implements OnInit {
  categories: Category[] = [];

  // EventEmitter to notify the ProductListComponent when a category is clicked
  @Output() categorySelected = new EventEmitter<string>();

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (data: Category[]) => {
        this.categories = data;
      },
      error: (error: unknown) => {
        console.error('Error fetching categories:', error);
      },
      complete: () => {
        console.log('Category data fetching complete');
      },
    });
  }

  // Method to handle category click
  onCategoryClick(categoryId: string): void {
    this.categorySelected.emit(categoryId);
  }
}
