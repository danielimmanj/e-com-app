import { Component, Input, OnInit } from '@angular/core';
import { Product } from '../../model/product.model';
import { CommonModule } from '@angular/common';
import { WishlistService } from '../../services/wishlist/wishlist.service';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  @Input() product!: Product;
  isInWishlist = false;
  constructor(private wishlistService: WishlistService) {}

  ngOnInit(): void {
    this.isInWishlist = this.wishlistService.isInWishlist(this.product);
  }

  toggleWishlist(): void {
    if (this.isInWishlist) {
      this.wishlistService.removeFromWishlist(this.product);
    } else {
      this.wishlistService.addToWishlist(this.product);
    }
    this.isInWishlist = !this.isInWishlist;
  }
}
