import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth-service';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  const excludedUrls = ['/login', '/register'];
  const isExcluded = excludedUrls.some(url => req.url.includes(url));

  let authReq = req;
  
  if (token && !isExcluded) {
    authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
  }

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      
      if (error.status === 401 || error.status === 403) {
        console.warn('Session expired or unauthorised. Logging out...');
        authService.logout();
      }

      return throwError(() => error);
    })
  );
};