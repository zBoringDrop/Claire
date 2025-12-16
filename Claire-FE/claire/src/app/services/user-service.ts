import { Injectable } from '@angular/core';
import { User, UserRegistration } from '../types/user.type';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private API_BASE_URL = environment.API_URL + '/user'
  private API_REGISTER = this.API_BASE_URL + '/register'
  private API_PROFILE = this.API_BASE_URL + '/me'

  constructor(private http: HttpClient) {}

  register(user: UserRegistration): Observable<User> {
    return this.http.post<User>(this.API_REGISTER, user)
  }

  me(): Observable<User> {
    return this.http.get<User>(this.API_PROFILE)
  }
  
}
