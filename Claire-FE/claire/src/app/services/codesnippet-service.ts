import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { CodeSnippet, CodeSnippetPreview } from '../types/codesnippet.type';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CodesnippetService {

  private API_BASE_URL = environment.API_URL + '/codesnippet'
  private API_UPLOAD = this.API_BASE_URL + '/upload'
  private API_DELETE = this.API_BASE_URL + '/delete'
  private API_GET_ALL = this.API_BASE_URL + '/list'
  private API_GET_ALL_PREVIEWS = this.API_BASE_URL + '/list/preview'
  private API_GET_BY_ID = this.API_BASE_URL + '/get'
  private API_GET_PREVIEW_BY_ID = this.API_BASE_URL + '/get/preview'

  constructor(private http: HttpClient) {}

  uploadNew(snippet: CodeSnippet): Observable<CodeSnippet> {
    return this.http.post<CodeSnippet>(this.API_UPLOAD, snippet)
  }

  delete(id: number): Observable<CodeSnippet> {
    return this.http.delete<CodeSnippet>(this.API_DELETE + '/' + id)
  }

  getAll(): Observable<CodeSnippet[]> {
    return this.http.get<CodeSnippet[]>(this.API_GET_ALL)
  }

  listUserCodeSnippetsPreview(): Observable<CodeSnippetPreview[]> {
    return this.http.get<CodeSnippetPreview[]>(this.API_GET_ALL_PREVIEWS)
  }

  getById(snippetId: number): Observable<CodeSnippet> {
    return this.http.get<CodeSnippet>(this.API_GET_BY_ID + '/' + snippetId)
  }

  getPreviewById(snippetId: number): Observable<CodeSnippetPreview> {
    return this.http.get<CodeSnippetPreview>(this.API_GET_PREVIEW_BY_ID + '/' + snippetId)
  }
  
}
