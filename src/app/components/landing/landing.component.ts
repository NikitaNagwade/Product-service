import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent implements OnInit {
  products: any[] = [];
  searchTerm = '';
  currentPage = 0;
  totalPages = 0;
  totalElements = 0;
  pageSize = 6;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    const params = {
      page: this.currentPage.toString(),
      size: this.pageSize.toString(),
      ...(this.searchTerm && { search: this.searchTerm })
    };

    this.http.get('http://localhost:8080/public/products', { params }).subscribe({
      next: (response: any) => {
        this.products = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
      },
      error: (err) => console.error('Error loading products:', err)
    });
  }

  onSearch() {
    this.currentPage = 0;
    this.loadProducts();
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.loadProducts();
  }

  getPages(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }
}