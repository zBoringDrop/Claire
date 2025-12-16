import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth-service';
import { inject } from '@angular/core';
import { catchError, tap, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const excludedUrls = ['/login', '/register'];
  if (excludedUrls.some(url => req.url.includes(url))) {
    return next(req);
  }

  const authService = inject(AuthService)
  const token = authService.getToken()

  if (token) {
    const newReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    })

    return next(newReq).pipe(tap(event => {
        
      }),
      catchError((error: HttpErrorResponse) => {
        console.log("Request error: ", error.status)
        if (error.status === 403) {
          authService.logout()
        }
        return throwError(() => error)
      }) 
    )
  }
  
  return next(req).pipe(tap(event => {
        
      }),
      catchError((error: HttpErrorResponse) => {
        console.log("Resource not found: ", error.status)
        if (error.status === 403) {
          authService.logout()
        }
        return throwError(() => error)
      }) 
    )
};
