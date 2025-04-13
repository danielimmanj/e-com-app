import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../model/product.model';
import { CommonModule } from '@angular/common';
import { ProductComponent } from "../product/product.component";

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, ProductComponent],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css'],
})
export class ProductListComponent implements OnInit, OnChanges {
  @Input() selectedCategoryId: string | null = null;
  products: Product[] = [];
  filteredProducts: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.fetchAllProducts();
  }

  ngOnChanges(): void {
    this.filterProductsByCategory();
  }

  fetchAllProducts(): void {
    this.productService.getProducts().subscribe(
      (data) => {
        this.products = data;
        this.filteredProducts = this.products;
        this.filterProductsByCategory();
      },
      (error) => {
        console.error('Error fetching products:', error);
      },
    );
  }

  filterProductsByCategory(): void {
    if (this.selectedCategoryId) {
      this.productService
        .getProductsByCategoryId(this.selectedCategoryId)
        .subscribe(
          (data) => {
            this.filteredProducts = data;
          },
          (error) => {
            console.error('Error fetching products by category:', error);
          },
        );
    } else {
      this.filteredProducts = this.products;
    }
  }
}
