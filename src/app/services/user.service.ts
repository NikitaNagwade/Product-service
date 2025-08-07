import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/user';

  constructor(private http: HttpClient) {}

  // Cart operations
  addToCart(product: any, quantity: number) {
    return this.http.post(`${this.baseUrl}/cart`, { product, quantity }, { responseType: 'text' });
  }

  getCart() {
    return this.http.get(`${this.baseUrl}/cart`);
  }

  removeFromCart(id: number) {
    return this.http.delete(`${this.baseUrl}/cart/${id}`, { responseType: 'text' });
  }

  // Wishlist operations
  addToWishlist(product: any) {
    return this.http.post(`${this.baseUrl}/wishlist`, { product }, { responseType: 'text' });
  }

  getWishlist() {
    return this.http.get(`${this.baseUrl}/wishlist`);
  }

  removeFromWishlist(id: number) {
    return this.http.delete(`${this.baseUrl}/wishlist/${id}`, { responseType: 'text' });
  }

  // Purchase operations
  purchaseProduct(product: any, quantity: number, totalPrice: number) {
    return this.http.post(`${this.baseUrl}/purchase`, { product, quantity, totalPrice }, { responseType: 'text' });
  }

  getPurchases() {
    return this.http.get(`${this.baseUrl}/purchases`);
  }

  getProducts() {
    return this.http.get(`${this.baseUrl}/products`);
  }
}