import { Component } from '@angular/core';
import { CategoryComponent } from "../category/category.component";
import { SlidingBannerComponent } from "../sliding-banner/sliding-banner.component";
import { ProductListComponent } from "../product-list/product-list.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CategoryComponent, SlidingBannerComponent, ProductListComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
