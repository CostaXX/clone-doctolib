import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './core/components/header/header';
import { Footer } from './core/components/footer/footer';
import { CoreModule } from './core/core-module';

@Component({
  selector: 'app-root',
  imports: [CoreModule,RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('frontend');
}
