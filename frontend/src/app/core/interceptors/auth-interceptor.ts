import { HttpEvent, HttpHandlerFn, HttpHeaders, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth-service';
import { Observable, throwError, catchError, switchMap } from 'rxjs';
import { Router } from '@angular/router';

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getAuthToken();
   if (!token) {
    return next(req);
  }
  
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        return authService.revokeToken().pipe(
          switchMap(() => {
            return next(req);
          }),
          catchError((refreshError) => {
            // Si le rafraîchissement échoue, déconnectez l'utilisateur
            authService.logOut();
            return throwError(() => refreshError);
          })
        );
      }
      return throwError(() => error);
    })
  );
}