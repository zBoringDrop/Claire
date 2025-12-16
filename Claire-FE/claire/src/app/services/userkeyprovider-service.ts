import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { Observable } from 'rxjs';
import { UserProviderApiKey, UserProviderApiKeyPreview } from '../types/aiprovider.type';
import { GenericResponse } from '../types/genericresponse.type';

@Injectable({
  providedIn: 'root'
})
export class UserkeyproviderService {

  private API_BASE_URL = environment.API_URL + '/user/provider/key'
  private API_REGISTER_NEW_USER_PROVIDER_KEY = this.API_BASE_URL + '/add/key'
  private API_ALL_USER_PROVIDERS_AND_KEYS = this.API_BASE_URL + '/all'
  private API_CHECK_USER_PROVIDER_KEY = this.API_BASE_URL + "/exists/provider"
  private API_UPDATE_USER_PROVIDER_KEY = this.API_BASE_URL + "/update"
  private API_DELETE_USER_PROVIDER_KEY = this.API_BASE_URL + "/delete"

  constructor(private http: HttpClient) {}
  
  getAll(): Observable<UserProviderApiKeyPreview[]> {
    return this.http.get<UserProviderApiKeyPreview[]>(this.API_ALL_USER_PROVIDERS_AND_KEYS)
  }

  existsUserKey(userId: number, providerId: number): Observable<boolean> {
    return this.http.get<boolean>(this.API_CHECK_USER_PROVIDER_KEY, {
      params: {
        userId: userId,
        providerId: providerId
      }
    });
  }

  addNewKey(request: UserProviderApiKey): Observable<GenericResponse> {
    return this.http.post<GenericResponse>(this.API_REGISTER_NEW_USER_PROVIDER_KEY, request)
  }

  updateKey(request: UserProviderApiKey): Observable<GenericResponse> {
    return this.http.patch<GenericResponse>(this.API_UPDATE_USER_PROVIDER_KEY, request)
  }

  deleteKey(idToDelete: number): Observable<GenericResponse> {
    return this.http.delete<GenericResponse>(this.API_DELETE_USER_PROVIDER_KEY + '/' + idToDelete)
  }
}
