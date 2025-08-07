import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = `http://localhost:8080`;

  constructor(private http: HttpClient) {}

  getProducts() {
    return this.http.get(`${this.baseUrl}/user/products`);
  }

  createProduct(product: any) {
    return this.http.post(`${this.baseUrl}/admin/products`, product);
  }

  updateProduct(id: any, product: any) {
    return this.http.put(`${this.baseUrl}/admin/products/${id}`, product);
  }

  deleteProduct(id: number) {
    return this.http.delete(`${this.baseUrl}/admin/products/${id}`);
  }
}
