import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './wishlist.component.html'
})
export class WishlistComponent implements OnInit {
  wishlistItems: any[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadWishlist();
  }

  loadWishlist() {
    this.userService.getWishlist().subscribe({
      next: (res) => {
        this.wishlistItems = res as any[];
      },
      error: (err) => {
        console.error('Error loading wishlist:', err);
        this.wishlistItems = [];
      }
    });
  }

  removeFromWishlist(id: number) {
    this.userService.removeFromWishlist(id).subscribe(() => {
      this.loadWishlist();
    });
  }

  addToCart(product: any) {
    this.userService.addToCart(product, 1).subscribe(() => {
      alert('Added to cart!');
    });
  }
}