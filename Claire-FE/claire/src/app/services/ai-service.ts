import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AI, AIPreview } from '../types/tool.type';

@Injectable({
  providedIn: 'root'
})
export class AiService {

  constructor(private http: HttpClient) {}

  private API_BASE_URL = environment.API_URL + '/tools/ai'
  private API_ALL_MODELS = this.API_BASE_URL + '/models'
  private API_GET_MODEL_PREVIEW = this.API_BASE_URL + '/get/preview'
  private API_USER_AVAILABLE_MODELS = this.API_BASE_URL + '/available'
  private API_ENABLED_MODELS = this.API_BASE_URL + '/models/enabled'
  private API_GET_BY_ID = this.API_BASE_URL + '/get'

  getAllModels(): Observable<AI[]> {
    return this.http.get<AI[]>(this.API_ALL_MODELS)
  }

  getModelpreview(aiId: number): Observable<AIPreview> {
    return this.http.get<AIPreview>(this.API_GET_MODEL_PREVIEW + '/' + aiId)
  }

  getUserAvailableModels(): Observable<AIPreview[]> {
    return this.http.get<AIPreview[]>(this.API_USER_AVAILABLE_MODELS)
  }

  getEnabledModels(): Observable<AI[]> {
    return this.http.get<AI[]>(this.API_ENABLED_MODELS)
  }

  getById(aiId: number): Observable<AI> {
    return this.http.get<AI>(this.API_GET_BY_ID + '/' + aiId)
  }
  
}
