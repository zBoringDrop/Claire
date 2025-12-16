import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { Observable } from 'rxjs';
import { ProgrammingLanguage } from '../types/programminglanguage.type';

@Injectable({
  providedIn: 'root',
})
export class ProgramminglanguageService {

  private API_BASE_URL = environment.API_URL + '/programming-languages'
  private API_ALL_PROGRAMMING_LANGUAGES = this.API_BASE_URL + '/all'

  constructor(private http: HttpClient) {}

  getById(id: number): Observable<ProgrammingLanguage> {
    return this.http.get<ProgrammingLanguage>(this.API_BASE_URL + '/' + id)
  }
  
  getAll(): Observable<ProgrammingLanguage[]> {
    return this.http.get<ProgrammingLanguage[]>(this.API_ALL_PROGRAMMING_LANGUAGES)
  }
}
