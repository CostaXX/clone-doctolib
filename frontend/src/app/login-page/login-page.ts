import { Component, ElementRef, ViewChild } from '@angular/core';
import { AuthService } from '../services/auth-service';

@Component({
  selector: 'app-login-page',
  imports: [],
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss'
})
export class LoginPage {
  @ViewChild('emailInput') emailInput!: ElementRef<HTMLInputElement>;
  @ViewChild('passwordInput') passwordInput!: ElementRef<HTMLInputElement>;
  @ViewChild('showPassBtn') showPassBtn!: ElementRef<HTMLButtonElement>;
  @ViewChild('submitInput') submitInput!: ElementRef<HTMLButtonElement>;

  
  passwordVisible = false;

  constructor(private authService: AuthService) {}

  togglePassword(): void {
    const input = this.passwordInput.nativeElement;
    this.passwordVisible = !this.passwordVisible;
    input.type = this.passwordVisible ? 'text' : 'password';
    this.showPassBtn.nativeElement.setAttribute('aria-pressed', String(this.passwordVisible));
  }

  login(event: Event): void {
    event.preventDefault();
    // event
    this.authService.login({
      username: this.emailInput.nativeElement.value,
      password: this.passwordInput.nativeElement.value}).subscribe({
        next: (response) => {
          console.log('Login successful:', response);
          this.authService.saveToken(response.token);
        }
      });
      
    // console.log(this.emailInput.nativeElement.value);
    // console.log(this.passwordInput.nativeElement.value);
    
    // this.submitInput.nativeElement.click();
  }

}
