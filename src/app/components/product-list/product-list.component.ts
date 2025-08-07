import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './product-list.component.html'
})
export class ProductListComponent implements OnInit {
  products: any[] = [];
  isAdmin = false;

  constructor(
    private productService: ProductService,
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log('Token:', this.authService.getToken());
    this.userService.getProducts().subscribe({
      next: (res) => {
        this.products = res as any[];
        console.log('Products loaded:', this.products);
      },
      error: (err) => {
        console.error('Error loading products:', err);
      }
    });
    this.checkAdminRole();
  }

  checkAdminRole() {
    const token = this.authService.getToken();
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      this.isAdmin = payload.role === 'ADMIN';
    }
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe(() => {
      this.products = this.products.filter(p => p.id !== id);
    });
  }

  addToCart(product: any) {
    this.userService.addToCart(product, 1).subscribe(() => {
      alert('Added to cart!');
    });
  }

  addToWishlist(product: any) {
    this.userService.addToWishlist(product).subscribe(() => {
      alert('Added to wishlist!');
    });
  }

  purchaseProduct(product: any) {
    const options = {
      key: 'rzp_test_3kwhCQ4KV49QuV',
      amount: product.price * 100, // Amount in paise
      currency: 'INR',
      name: 'Product Store',
      description: product.name,
      handler: (response: any) => {
        this.userService.purchaseProduct(product, 1, product.price).subscribe(() => {
          alert('Payment successful! Order ID: ' + response.razorpay_payment_id);
        });
      },
      prefill: {
        name: 'Customer',
        email: 'customer@example.com'
      },
      theme: {
        color: '#3399cc'
      }
    };
    
    const rzp = new (window as any).Razorpay(options);
    rzp.open();
  }

  editProduct(id: number) {
    console.log('Editing product with ID:', id);
    this.router.navigate(['/product-form'], { queryParams: { id } });
  }

  logout() {
    this.authService.logout();
  }
}
