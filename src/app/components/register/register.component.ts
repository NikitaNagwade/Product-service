import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // <-- For ngModel
import { RouterModule } from '@angular/router'; // if you're using routerLink etc.

import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true, // ✅ Ensure standalone is enabled
  imports: [
    CommonModule,
    FormsModule,
    RouterModule // if needed
  ],
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  name = '';
  email = '';
  mobileNo = '';
  password = '';
  error = '';
  success = '';
  nameError = '';
  emailError = '';
  mobileError = '';

  constructor(private authService: AuthService, private router: Router) {}

  validateName() {
    this.nameError = this.name.trim().length < 2 ? 'Enter valid name (min 2 characters)' : '';
  }

  validateEmail() {
    this.emailError = !this.email.includes('@') ? 'Enter valid email' : '';
  }

  validateMobile() {
    this.mobileError = this.mobileNo.trim().length < 10 ? 'Enter valid mobile number (min 10 digits)' : '';
  }

  register() {
    this.authService.register({ 
      name: this.name, 
      email: this.email, 
      mobileNo: this.mobileNo, 
      password: this.password 
    }).subscribe({
      next: () => {
        this.success = 'Registration successful! You can now log in.';
        this.error = '';
        setTimeout(() => this.router.navigate(['/login']), 1500);
      },
      error: (err) => {
        this.error = err.error?.message || 'Registration failed';
        this.success = '';
      }
    });
  }
}
