import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { AuthService } from '../core/services/auth-service';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';



@Component({
  selector: 'app-login-page',
  imports: [RouterLink, FormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss'
})
export class LoginPage {
  @ViewChild('emailInput') emailInput!: ElementRef<HTMLInputElement>;
  @ViewChild('passwordInput') passwordInput!: ElementRef<HTMLInputElement>;
  @ViewChild('showPassBtn') showPassBtn!: ElementRef<HTMLButtonElement>;
  
  
  passwordVisible = false;

  private authService = inject(AuthService);
  private router = inject(Router);

    username = '';
    password = '';

  togglePassword(): void {
    const input = this.passwordInput.nativeElement;
    this.passwordVisible = !this.passwordVisible;
    input.type = this.passwordVisible ? 'text' : 'password';
    this.showPassBtn.nativeElement.setAttribute('aria-pressed', String(this.passwordVisible));
  }

  onSubmit() {
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        console.log('Logged in successfully');
        this.router.navigateByUrl('/login');
      },
      error: (error: HttpErrorResponse) => {  
        console.error('Login failed', error);
        // Afficher un message d'erreur à l'utilisateur
      }
    });
  }
}
