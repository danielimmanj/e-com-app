import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../model/product.model';
import { CommonModule } from '@angular/common';
import { CategoryService } from '../../services/category/category.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit {
  categories: string[] = [];
  products: Product[] = [];
  filteredProducts: Product[] = [];

  constructor(private productService: ProductService, private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.fetchCategories();
    this.fetchProducts();
  }

  fetchCategories(): void {
    this.categoryService.getCategories().subscribe(
      (data) => {
        this.categories = data.map((category) => category.name);
      },
      (error) => {
        console.error('Error fetching categories:', error);
      }
    );
  }

  fetchProducts(): void {
    this.productService.getProducts().subscribe(
      (data) => {
        this.products = data;
        this.filteredProducts = this.products; // Initialize with all products
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }

  onCategoryClick(category: string): void {
    this.filteredProducts = this.products.filter(
      (product) => product.category.name === category
    );
  }
}
