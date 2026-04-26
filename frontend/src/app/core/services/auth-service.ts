import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { User } from '../models/user';
import { LoginResponse } from '../models/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient)
  private _currentUser = signal<LoginResponse | null>(null)

  

  login(username: string, password: string): Observable<{
    user: LoginResponse
  }> 
  {
    return this.http.post<{
    user: LoginResponse
  }>('http://localhost:8081/api/v1/connexion', { username, password }, { withCredentials: true })
      .pipe(
        tap(response => {
          // Les deux tokens sont automatiquement stockés dans des cookies HTTP-only
          // Nous mettons à jour l'état de l'utilisateur connecté
          // 
          this._currentUser.set(response.user);
          
          console.log(typeof response.user); // Devrait être une chaîne de caractères
          console.log('Login successful, user data:', response);
          
        })
      );
  }

  // Méthode pour rafraîchir les tokens. Utilisée par l'intercepteur HTTP
  revokeToken(): Observable<any> {
    return this.http.post<any>('/api/revoke-token', {}, { withCredentials: true })
      .pipe(
        tap(response => {
          // Les nouveaux tokens sont automatiquement stockés dans des cookies HTTP-only
          console.log('Tokens refreshed successfully');
        })
      );
  }

  logout(): Observable<any> {
    return this.http.post<any>('/api/logout', {}, { withCredentials: true })
      .pipe(
        tap(() => {
          // Le backend devrait supprimer les cookies
          this._currentUser.set(null);
        })
      );
  }
}