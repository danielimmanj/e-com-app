import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../model/product.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit, OnChanges {
  @Input() selectedCategoryId: string | null = null; // Input property for category ID
  products: Product[] = [];
  filteredProducts: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.fetchAllProducts();
  }

  fetchAllProducts(): void {
    this.productService.getProducts().subscribe(
      (data) => {
        this.products = data;
        this.filteredProducts = this.products; // Initialize with all products
        this.filterProductsByCategory(); // Filter products if a category is selected
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }

  ngOnChanges(): void {
    this.filterProductsByCategory(); // Re-filter when the selected category changes
  }

  // Filter products based on the selected category ID
  filterProductsByCategory(): void {
    if (this.selectedCategoryId) {
      this.productService.getProductsByCategoryId(this.selectedCategoryId).subscribe(
        (data) => {
          this.filteredProducts = data;
        },
        (error) => {
          console.error('Error fetching products by category:', error);
        }
      );
    } else {
      this.filteredProducts = this.products; // Show all products if no category is selected
    }
  }
}
