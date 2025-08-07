import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-wishlists',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-wishlists.component.html'
})
export class AdminWishlistsComponent implements OnInit {
  wishlists: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get('http://localhost:8080/admin/wishlists').subscribe({
      next: (data: any) => this.wishlists = data,
      error: (err) => console.error('Error loading wishlists:', err)
    });
  }
}