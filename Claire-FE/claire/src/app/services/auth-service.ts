import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { UserLogin } from '../types/user.type';
import { AuthenticationResponse } from '../types/auth.type';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../environment';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private API_BASE_URL = environment.API_URL + '/auth';
  private API_LOGIN = this.API_BASE_URL + '/login';
  
  private authSecretKey = 'Bearer Token';

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private http: HttpClient,
    private router: Router
  ) {}

  isAuthenticatedUser(): boolean {
    if (!isPlatformBrowser(this.platformId)) {
      return false;
    }

    const token = this.getToken();
    if (!token) return false;

    try {
      const decoded: any = jwtDecode(token);
      const currentTime = Date.now() / 1000;

      if (decoded.exp < currentTime) {
        this.logout(); 
        return false;
      }

      return true;
    } catch (error) {
      return false;
    }
  }

  login(userLogin: UserLogin): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(this.API_LOGIN, userLogin).pipe(
      tap(res => {
        this.setJwtToken(res.jwt_token);
      })
    );
  }

  setJwtToken(jwtToken: string) {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.authSecretKey, jwtToken);
    }
  }

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem(this.authSecretKey);
    }
    return null;
  }

  logout() {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem(this.authSecretKey);
    }
    this.router.navigate(['/login']);
  }
}