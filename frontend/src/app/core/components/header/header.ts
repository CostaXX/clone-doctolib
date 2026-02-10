import { Component, ViewChild, ElementRef  } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [RouterModule],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  @ViewChild('maDiv') maDiv!: ElementRef;
  showOverlay(): void {
    this.maDiv.nativeElement.classList.toggle('navActive');
  }
}