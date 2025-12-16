import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { Observable } from 'rxjs';
import { AIProvider } from '../types/aiprovider.type';

@Injectable({
  providedIn: 'root'
})
export class ProviderService {

  private API_BASE_URL = environment.API_URL + '/ai/providers'
  private API_ALL_PROVIDERS = this.API_BASE_URL + '/all'

  constructor(private http: HttpClient) {}
  
  getAll(): Observable<AIProvider[]> {
    return this.http.get<AIProvider[]>(this.API_ALL_PROVIDERS)
  }
}
