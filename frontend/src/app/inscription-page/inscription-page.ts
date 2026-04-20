import { Component, ElementRef, ViewChild  } from '@angular/core';
import { RedirectCommand, RouterLink } from '@angular/router';
import { InscriptionService } from '../core/services/inscription-service';


@Component({
  selector: 'app-inscription-page',
  imports: [RouterLink],
  templateUrl: './inscription-page.html',
  styleUrl: './inscription-page.scss'
})
export class InscriptionPage {

  @ViewChild('nomInput') nomInput!: ElementRef<HTMLInputElement>;
  @ViewChild('prenomInput') prenomInput!: ElementRef<HTMLInputElement>;
  @ViewChild('dateNaissanceInput') dateNaissanceInput!: ElementRef<HTMLInputElement>;
  @ViewChild('sexeInput') sexeInput!: ElementRef<HTMLInputElement>;
  @ViewChild('emailInput') emailInput!: ElementRef<HTMLInputElement>;
  @ViewChild('passwordInput') passwordInput!: ElementRef<HTMLInputElement>;
  @ViewChild('telephoneInput') telephoneInput!: ElementRef<HTMLInputElement>;
  @ViewChild('typeInput') typeInput!: ElementRef<HTMLInputElement>;
  @ViewChild('showPassBtn') showPassBtn!: ElementRef<HTMLInputElement>;


  passwordVisible = false;

  constructor(private inscriptionService: InscriptionService) {}
    
    togglePassword(): void {
    const input = this.passwordInput.nativeElement;
    this.passwordVisible = !this.passwordVisible;
    input.type = this.passwordVisible ? 'text' : 'password';
    this.showPassBtn.nativeElement.setAttribute('aria-pressed', String(this.passwordVisible));
  }

  inscription(event: Event): void {
    event.preventDefault();
    let sexeValueNumber:number = Number(this.sexeInput.nativeElement.value);
    this.inscriptionService.inscription({
      email: this.emailInput.nativeElement.value,
      password: this.passwordInput.nativeElement.value,
      nom: this.nomInput.nativeElement.value,
      prenom: this.prenomInput.nativeElement.value,
      type: this.typeInput.nativeElement.value,
      telephone: this.telephoneInput.nativeElement.value,
      dateNaissance: this.dateNaissanceInput.nativeElement.value,
      sexe: sexeValueNumber
    }).subscribe({
      next: (response) => {
        console.log('Inscription successful:', response);
        // Rediriger vers la page de validation après une inscription réussie
        window.location.href = '/validation';
      }
    });
    
  }
}
