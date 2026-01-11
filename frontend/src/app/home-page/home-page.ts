import { Component } from '@angular/core';
import { Hero } from '../hero/hero';

@Component({
  
  selector: 'app-home-page',
  imports: [Hero],
  templateUrl: './home-page.html',
  styleUrl: './home-page.scss'
})
export class HomePage {
  title = 'clone-doctolib';
}
