import { Component, ViewChild, ElementRef  } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-header',
  imports: [RouterModule],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {

  constructor(public auth: AuthService) {}

  @ViewChild('maDiv') maDiv!: ElementRef;
  showOverlay(): void {
    this.maDiv.nativeElement.classList.toggle('navActive');
  }
}