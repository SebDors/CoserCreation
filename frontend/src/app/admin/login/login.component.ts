import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email = '';
  password = '';
  errorMessage = '';

  constructor(private authService: AuthService) { }

  onLogin(): void {
    if (!this.authService.login(this.email, this.password)) {
      this.errorMessage = 'Email ou mot de passe incorrect';
    }
  }
}
