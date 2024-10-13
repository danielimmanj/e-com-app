import { Component, OnInit } from '@angular/core';

interface Product {
  id: number;
  name: string;
  category: string;
}

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
})
export class ProductListComponent implements OnInit {
  categories: string[] = [
    "Women's Fashion",
    "Men's Fashion",
    'Electronics',
    'Home & Lifestyle',
    'Medicine',
    'Sports & Outdoor',
    "Baby's & Toys",
    'Groceries & Pets',
    'Health & Beauty',
  ];

  products: Product[] = [
    { id: 1, name: 'Product 1', category: "Women's Fashion" },
    { id: 2, name: 'Product 2', category: "Men's Fashion" },
    { id: 3, name: 'Product 3', category: 'Electronics' },
    { id: 4, name: 'Product 4', category: 'Home & Lifestyle' },
    { id: 5, name: 'Product 5', category: 'Medicine' },
    { id: 6, name: 'Product 6', category: 'Sports & Outdoor' },
    { id: 7, name: 'Product 7', category: "Baby's & Toys" },
    { id: 8, name: 'Product 8', category: 'Groceries & Pets' },
    { id: 9, name: 'Product 9', category: 'Health & Beauty' },
  ];

  filteredProducts: Product[] = [];

  ngOnInit(): void {
    // Initialize the product list with all products or a specific default category
    this.filteredProducts = this.products;
  }

  onCategoryClick(category: string): void {
    // Filter products based on the selected category
    this.filteredProducts = this.products.filter(product => product.category === category);
  }
}