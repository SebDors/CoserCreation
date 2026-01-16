import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticated = false;

  constructor(private router: Router) { }

  login(email: string, password: string): boolean {
    // For demonstration purposes, using a hardcoded email and password.
    // In a real application, you would use a secure authentication method.
    if (email === 'admin@example.com' && password === 'admin') {
      this.isAuthenticated = true;
      this.router.navigate(['/admin/dashboard']);
      return true;
    }
    return false;
  }

  logout(): void {
    this.isAuthenticated = false;
    this.router.navigate(['/admin/login']);
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated;
  }
}
