import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent implements OnInit {
  product = {
    id: null,
    name: '',
    price: 0,
    description: ''
  };
  error = '';
  success = '';
  isEdit = false;

  constructor(
    private productService: ProductService, 
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    const productId = this.route.snapshot.queryParams['id'];
    console.log('Product ID from query params:', productId);
    if (productId) {
      this.isEdit = true;
      this.loadProduct(productId);
    }
  }

  loadProduct(id: number) {
    console.log('Loading product with ID:', id);
    this.userService.getProducts().subscribe({
      next: (response) => {
        const products = response as any[];
        console.log('All products:', products);
        const product = products.find(p => p.id == id);
        console.log('Found product:', product);
        if (product) {
          this.product.id = product.id;
          this.product.name = product.name;
          this.product.price = product.price;
          this.product.description = product.description || '';
          console.log('Product loaded:', this.product);
        } else {
          console.log('Product not found with ID:', id);
        }
      },
      error: (err) => {
        console.error('Error loading products:', err);
        this.error = 'Failed to load product';
      }
    });
  }

  saveProduct() {
    if (this.isEdit) {
      this.productService.updateProduct(this.product.id, this.product).subscribe({
        next: () => {
          this.success = 'Product updated successfully!';
          setTimeout(() => this.router.navigate(['/products']), 1500);
        },
        error: () => this.error = 'Failed to update product'
      });
    } else {
      this.productService.createProduct(this.product).subscribe({
        next: () => {
          this.success = 'Product created successfully!';
          setTimeout(() => this.router.navigate(['/products']), 1500);
        },
        error: () => this.error = 'Failed to create product'
      });
    }
  }
}
