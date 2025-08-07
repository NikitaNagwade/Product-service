import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-admin-purchases',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-purchases.component.html'
})
export class AdminPurchasesComponent implements OnInit {
  purchases: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get('http://localhost:8080/admin/purchases').subscribe({
      next: (data: any) => this.purchases = data,
      error: (err) => console.error('Error loading purchases:', err)
    });
  }
}