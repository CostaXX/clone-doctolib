import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InscriptionService {
  private apiUrl = 'http://localhost:8081/api/v1';

  constructor(private http: HttpClient) {}

  inscription(data : {
    email: string;
    password: string;
    nom: string;
    prenom: string;
    type: string;
    telephone: string;
    dateNaissance: string;
    sexe:number
  }){
    return this.http.post<any>(`${this.apiUrl}/inscription`,data);
  }
}
