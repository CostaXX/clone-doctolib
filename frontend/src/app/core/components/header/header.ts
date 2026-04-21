import { Component, ViewChild, ElementRef, inject  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-header',
  imports: [RouterModule, CommonModule],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {

  constructor(public auth: AuthService) {
    
  }

  ngonInit() {
    console.log('Current user on header init:', this.auth.currentUser()?.user.nom);
  }
  authService = inject(AuthService);
  user = this.authService.currentUser()?.user.nom;

  @ViewChild('maDiv') maDiv!: ElementRef;
  showOverlay(): void {
    this.maDiv.nativeElement.classList.toggle('navActive');
  }
}