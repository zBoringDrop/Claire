import { Injectable } from '@angular/core';
import { environment } from '../environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Category } from '../types/category.type';
import { Observable } from 'rxjs';
import { AnalysisIdCategoryName, AnalysisIdsCategoryName } from '../types/category.type';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private API_BASE_URL = environment.API_URL + '/analysis/category'
  private API_ALL_CATEGORIES = this.API_BASE_URL + '/all'
  private API_FIND_CATEGORIES_BY_ANALYSIS_ID = this.API_BASE_URL + '/names'
  private API_FIND_CATEGORIES_BY_ANALYSIS_IDS = this.API_BASE_URL + '/analysisids'

  constructor(private http: HttpClient) {}

  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.API_ALL_CATEGORIES)
  }

  getCategoriesByAnalysisId(analysisId: number): Observable<AnalysisIdCategoryName[]> {
    return this.http.get<AnalysisIdCategoryName[]>(this.API_FIND_CATEGORIES_BY_ANALYSIS_ID + '/' + analysisId);
  }
  
  getBatchAnalysisCategories(analysisIds: number[]): Observable<AnalysisIdsCategoryName[]> {
    const params = new HttpParams().set('analysisIds', analysisIds.join(','));
    return this.http.get<AnalysisIdsCategoryName[]>(this.API_FIND_CATEGORIES_BY_ANALYSIS_IDS, { params });
  }
}
