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
  private _isConnected = signal(!!this.getAuthToken());
  isConnected = this._isConnected.asReadonly();

  

  login(username: string, password: string) {
    return this.http.post<{ token: string }>('/api/login', { username, password }, { withCredentials: true })
  }

  saveAuthToken(token: string) {
    localStorage.setItem('token', token);
    this._isConnected.set(true);
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

  getAuthToken() {
    return localStorage.getItem('token');
  }

  logOut() {
    localStorage.removeItem('token');
    this._isConnected.set(false);
  }
}