import { Injectable } from '@angular/core';
import { Product } from '../../model/product.model';

@Injectable({
  providedIn: 'root',
})
export class WishlistService {
  private wishlist = new Set<Product>();

  addToWishlist(product: Product): void {
    this.wishlist.add(product);
  }

  removeFromWishlist(product: Product): void {
    this.wishlist.delete(product);
  }

  isInWishlist(product: Product): boolean {
    return this.wishlist.has(product);
  }

  getWishlist(): Product[] {
    return Array.from(this.wishlist);
  }
}
