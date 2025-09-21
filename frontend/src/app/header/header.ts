import { Component, ViewChild, ElementRef  } from '@angular/core';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header {
  @ViewChild('maDiv') maDiv!: ElementRef;
  showOverlay(): void {
    this.maDiv.nativeElement.classList.toggle('navActive');
  }
}