import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FileCode, FileCodePreview } from '../types/file.type';
import { Observable } from 'rxjs';
import { environment } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  private API_BASE_URL = environment.API_URL + '/file'
  private API_CREATE = this.API_BASE_URL + '/upload'
  private API_DELETE = this.API_BASE_URL + '/delete'
  private API_GET_ALL =  this.API_BASE_URL + '/list'
  private API_GET_ALL_PREVIEW =  this.API_BASE_URL + '/list/preview'
  private API_GET_BY_ID =  this.API_BASE_URL + '/get'
  private API_GET_PREVIEW_BY_ID =  this.API_BASE_URL + '/get/preview'

  constructor(private http: HttpClient) {}

  createNew(newFile: FormData): Observable<FileCode> {
    return this.http.post<FileCode>(this.API_CREATE, newFile);
  }

  delete(id: number): Observable<FileCode> {
    return this.http.delete<FileCode>(this.API_DELETE + '/' + id);
  }

  getAll(): Observable<FileCode[]> {
    return this.http.get<FileCode[]>(this.API_GET_ALL)
  }

  listAllUserFilePreviews(): Observable<FileCodePreview[]> {
    return this.http.get<FileCodePreview[]>(this.API_GET_ALL_PREVIEW)
  }

  getById(fileId: number): Observable<FileCode> {
    return this.http.get<FileCode>(this.API_GET_BY_ID + '/' + fileId)
  }

  getPreviewById(fileId: number): Observable<FileCodePreview> {
    return this.http.get<FileCodePreview>(this.API_GET_PREVIEW_BY_ID + '/' + fileId)
  }
  
}
