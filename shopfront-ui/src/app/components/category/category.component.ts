import { Component, OnInit } from '@angular/core';
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
}
