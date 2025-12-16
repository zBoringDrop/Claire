import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { AnalysisRequest, AnalysisPreview, AnalysisSchema } from '../types/analysis.type';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AnalysisService {

  private API_BASE_URL = environment.API_URL + '/analysis'
  private API_BASE_URL_SNIPPET = this.API_BASE_URL + "/codesnippet"
  private API_BASE_URL_FILE = this.API_BASE_URL + "/file"
  private API_FIND_ANALYSIS_BY_ID = this.API_BASE_URL
  private API_FIND_ANALYSIS_BY_FILE_ID = this.API_BASE_URL + '/file'
  private API_FIND_ANALYSIS_BY_SNIPPET_ID = this.API_BASE_URL + '/snippet'
  private API_DELETE_ANALYSIS_BY_ID = this.API_BASE_URL + "/delete"
  private API_FIND_ANALYSIS_BY_STATUS = this.API_BASE_URL + '/status'
  private API_GET_RUNNING_ANALYSIS = this.API_BASE_URL + '/running'
  private API_START_SNIPPET_ANALYSIS = this.API_BASE_URL_SNIPPET + "/analyze"
  private API_START_FILE_ANALYSIS = this.API_BASE_URL_FILE + "/analyze"
  private API_GET_ALL_USER_ANALYSIS_PREVIEWS = this.API_BASE_URL + "/previews"

  constructor(private http: HttpClient) {}

  getById(analysisId: number): Observable<AnalysisSchema> {
    return this.http.get<AnalysisSchema>(this.API_FIND_ANALYSIS_BY_ID + '/' + analysisId)
  }

  deleteAnalysisById(analysisId: number): Observable<AnalysisSchema> {
    return this.http.delete<AnalysisSchema>(this.API_DELETE_ANALYSIS_BY_ID + '/' + analysisId)
  }

  getAnalysisByStatus(status: string): Observable<AnalysisSchema[]> {
    return this.http.get<AnalysisSchema[]>(this.API_FIND_ANALYSIS_BY_STATUS + '/' + status)
  }

  getRunningAnalysis(): Observable<AnalysisPreview[]> {
    return this.http.get<AnalysisPreview[]>(this.API_GET_RUNNING_ANALYSIS)
  }

  getAllUserAnalysisPreviews(): Observable<AnalysisPreview[]> {
    return this.http.get<AnalysisPreview[]>(this.API_GET_ALL_USER_ANALYSIS_PREVIEWS)
  }

  analyzeSnippet(analysisRequest: AnalysisRequest): Observable<AnalysisSchema> {
    return this.http.post<AnalysisSchema>(this.API_START_SNIPPET_ANALYSIS, analysisRequest)
  }

  analyzeFile(analysisRequest: AnalysisRequest): Observable<AnalysisSchema> {
    return this.http.post<AnalysisSchema>(this.API_START_FILE_ANALYSIS, analysisRequest)
  }

  getPreviewsByUserAndFileId(fileId: number): Observable<AnalysisPreview[]> {
    return this.http.get<AnalysisPreview[]>(this.API_FIND_ANALYSIS_BY_FILE_ID + '/' + fileId + '/previews')
  }

  getPreviewsByUserAndSnippetId(snippetId: number): Observable<AnalysisPreview[]> {
    return this.http.get<AnalysisPreview[]>(this.API_FIND_ANALYSIS_BY_SNIPPET_ID + '/' + snippetId + '/previews')
  }

}
