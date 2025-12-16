import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { Observable } from 'rxjs';
import { GenericResponse } from '../types/genericresponse.type';

@Injectable({
  providedIn: 'root'
})
export class AimodelsyncService {

  private API_BASE_URL = environment.API_URL + '/synchroniser'
  private API_BASE_URL_SYNC_SPECIFIC_MODEL_BY_NAME = this.API_BASE_URL + "/sync/name"
  private API_BASE_URL_SYNC_SPECIFIC_MODEL_BY_ID = this.API_BASE_URL + "/sync/id"
  private API_BASE_URL_SYNC_ALL = this.API_BASE_URL + "/sync/all"

  constructor(private http: HttpClient) {}

  syncSpecificModelByName(modelName: string): Observable<GenericResponse> {
    return this.http.post<GenericResponse>(this.API_BASE_URL_SYNC_SPECIFIC_MODEL_BY_NAME + '/' + modelName, null)
  }

  syncSpecificModelById(modelId: number): Observable<GenericResponse> {
    return this.http.post<GenericResponse>(this.API_BASE_URL_SYNC_SPECIFIC_MODEL_BY_ID + '/' + modelId, null)
  }

  syncAllModels(): Observable<GenericResponse> {
    return this.http.post<GenericResponse>(this.API_BASE_URL_SYNC_ALL, null)
  }
  
}
