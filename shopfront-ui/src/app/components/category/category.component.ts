import { Component, OnInit } from '@angular/core';
import { CategoryModel } from '../../model/category.model';
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
  categories: CategoryModel[] = [];

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (data: CategoryModel[]) => {
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
