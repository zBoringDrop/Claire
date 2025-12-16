import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environment';
import { UserOllamaConfig } from '../types/userollamaconfig.type';

@Injectable({
  providedIn: 'root'
})
export class UserOllamaConfigService {

  private API_BASE_URL = environment.API_URL + '/ollama/user/config';
  private API_NEW = this.API_BASE_URL + '/new';
  private API_UPDATE = this.API_BASE_URL + '/update';
  private API_DELETE = this.API_BASE_URL + '/delete';
  private API_GET = this.API_BASE_URL + '/get';
  private API_EXISTS = this.API_BASE_URL + '/exists';

  constructor(private http: HttpClient) {}

  createNew(config: UserOllamaConfig): Observable<UserOllamaConfig> {
    return this.http.post<UserOllamaConfig>(this.API_NEW, config);
  }

  update(config: UserOllamaConfig): Observable<UserOllamaConfig> {
    return this.http.post<UserOllamaConfig>(this.API_UPDATE, config);
  }

  delete(): Observable<UserOllamaConfig> {
    return this.http.delete<UserOllamaConfig>(this.API_DELETE);
  }

  get(): Observable<UserOllamaConfig> {
    return this.http.get<UserOllamaConfig>(this.API_GET);
  }

  exists(): Observable<boolean> {
    return this.http.get<boolean>(this.API_EXISTS);
  }

}