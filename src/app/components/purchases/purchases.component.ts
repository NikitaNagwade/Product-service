import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-purchases',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './purchases.component.html'
})
export class PurchasesComponent implements OnInit {
  purchases: any[] = [];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getPurchases().subscribe(res => {
      this.purchases = res as any[];
    });
  }
}