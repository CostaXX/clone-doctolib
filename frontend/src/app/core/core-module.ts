import { Header } from './components/header/header';
import { Footer } from './components/footer/footer';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';




@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule,
    Header,
    Footer
  ],
  exports: [
    Header,
    Footer
  ],
  providers: [
    
  ]
})
export class CoreModule {

 }
