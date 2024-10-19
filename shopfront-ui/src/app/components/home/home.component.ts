import { Component } from '@angular/core';
import { CategoryService } from '../../services/category/category.service';
import { CategoryComponent } from '../category/category.component';
import { ProductListComponent } from '../product-list/product-list.component';
import { SlidingBannerComponent } from '../sliding-banner/sliding-banner.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CategoryComponent, SlidingBannerComponent, ProductListComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  selectedCategoryId: string | null = null; // Holds the selected category ID

  constructor(private categoryService: CategoryService) {}

  onCategorySelected(categoryId: string): void {
    this.selectedCategoryId = categoryId; // Update the selected category ID
  }
}
