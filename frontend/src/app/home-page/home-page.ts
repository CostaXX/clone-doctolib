import { Component, effect, inject, Injectable } from '@angular/core';
import { Hero } from '../hero/hero';
import { AuthService } from '../core/services/auth-service';

@Injectable({
  providedIn: 'root'
})
@Component({
  
  selector: 'app-home-page',
  imports: [Hero],
  templateUrl: './home-page.html',
  styleUrl: './home-page.scss'
})
export class HomePage {

  constructor(public auth: AuthService) {
    effect(() => {
      console.log('User changed:', this.authService.currentUser());
    });
  }


  authService = inject(AuthService);
  title = 'clone-doctolib';
}
