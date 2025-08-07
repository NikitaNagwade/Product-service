import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './cart.component.html'
})
export class CartComponent implements OnInit {
  cartItems: any[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart() {
    this.userService.getCart().subscribe({
      next: (res) => {
        this.cartItems = res as any[];
      },
      error: (err) => {
        console.error('Error loading cart:', err);
        this.cartItems = [];
      }
    });
  }

  removeFromCart(id: number) {
    this.userService.removeFromCart(id).subscribe({
      next: () => {
        this.loadCart();
      },
      error: (err) => {
        console.error('Error removing from cart:', err);
        this.loadCart(); // Reload anyway since the operation likely succeeded
      }
    });
  }

  purchaseAll() {
    this.cartItems.forEach(item => {
      this.userService.purchaseProduct(item.product, item.quantity, item.product.price * item.quantity).subscribe();
    });
    alert('All items purchased!');
    this.cartItems = [];
  }
}