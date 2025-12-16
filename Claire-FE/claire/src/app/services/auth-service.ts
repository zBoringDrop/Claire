import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { UserLogin } from '../types/user.type';
import { AuthenticationResponse } from '../types/auth.type';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private API_BASE_URL = environment.API_URL + '/auth';
  private API_LOGIN = this.API_BASE_URL + '/login';

  private isAuthenticated = false;
  private authSecretKey = 'Bearer Token';

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private http: HttpClient,
    private router: Router
  ) {
    if (isPlatformBrowser(this.platformId)) {
      this.isAuthenticated = !!localStorage.getItem(this.authSecretKey);
    }
  }

  isAuthenticatedUser(): boolean {
    return this.isAuthenticated;
  }

  login(userLogin: UserLogin): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(this.API_LOGIN, userLogin);
  }

  setLogin(userLogin: UserLogin) {
    this.login(userLogin).subscribe(res => {
      if (isPlatformBrowser(this.platformId)) {
        localStorage.setItem(this.authSecretKey, res.jwt_token);
      }
      this.isAuthenticated = true;
    });
  }

  setJwtToken(jwtToken: string) {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem(this.authSecretKey, jwtToken);
    }
    
    this.isAuthenticated = true;
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
    this.isAuthenticated = false;
    this.router.navigate(['/login']);
  }
}
