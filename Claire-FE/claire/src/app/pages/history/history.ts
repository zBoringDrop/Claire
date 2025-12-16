import { HttpErrorResponse } from '@angular/common/http';
import { Component, signal } from '@angular/core';
import { AnalysisPreview } from '../../types/analysis.type';
import { AnalysisService } from '../../services/analysis-service';
import { ProblemDetails } from '../../types/problemdetails';
import { ANALYSIS_STATUS } from '../../constants/analysis-status';
import { Subject, takeUntil, timer } from 'rxjs';
import { CategoryService } from '../../services/category-service';
import { AnalysisIdsCategoryName } from '../../types/category.type';
import { AnalysisCompleted } from '../../components/analysis-completed/analysis-completed';
import { AnalysisFailed } from "../../components/analysis-failed/analysis-failed";
import { AnalysisRunning } from "../../components/analysis-running/analysis-running";
import { getScoreColor } from '../../constants/analysis-score-color';

@Component({
  selector: 'app-history',
  imports: [AnalysisCompleted, AnalysisFailed, AnalysisRunning],
  templateUrl: './history.html',
  styleUrl: './history.css'
})
export class History {

  getScoreColor = getScoreColor

  private destroy$ = new Subject<void>();

  userCompletedAnalysis = signal<AnalysisPreview[]>([])
  loadingCompletedAnalysis = signal<boolean>(false)

  userPartialAnalysis = signal<AnalysisPreview[]>([])
  loadingPartialAnalysis = signal<boolean>(false)

  userFailedAnalysis = signal<AnalysisPreview[]>([])
  loadingFailedAnalysis = signal<boolean>(false)

  userRunningAnalysis = signal<AnalysisPreview[]>([])
  loadingRunningAnalysis = signal<boolean>(false)

  analysisNameCategories = signal<AnalysisIdsCategoryName[]>([])

  constructor(private analysisService: AnalysisService,
              private categoryService: CategoryService
  ) {}

  ngOnInit() {
    timer(0, 10000)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => this.fillAnalysisLists());
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  orderResultsByDate(res: AnalysisPreview[]) {
    res.sort((a, b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime())
  }

  private clearLoadingSignals() {
    this.userCompletedAnalysis.set([]);
    this.userPartialAnalysis.set([]);
    this.userFailedAnalysis.set([]);
    this.userRunningAnalysis.set([]);
    this.analysisNameCategories.set([]);
  }

  fillAnalysisLists() {

    this.updateLoadingStatus(true)

    this.analysisService.getAllUserAnalysisPreviews().subscribe({
      next: (previews: AnalysisPreview[]) => {

        this.orderResultsByDate(previews)

        this.updateLoadingStatus(false)

        const analysisIds = previews.map(p => p.id);
        if (!analysisIds.length) {
          this.clearLoadingSignals()
          return;
        }

        this.categoryService.getBatchAnalysisCategories(analysisIds).subscribe(categoryPairs => {
          this.analysisNameCategories.set(categoryPairs);

          const mergedPreviews = previews.map(preview => {
            const categories = categoryPairs
                .filter(c => c.analysis_id === preview.id)
                .map(c => c.category_name); 
            
            return { ...preview, categories };
          });

          this.userCompletedAnalysis.set(mergedPreviews.filter(e => e.status === ANALYSIS_STATUS.COMPLETED));
          this.userPartialAnalysis.set(mergedPreviews.filter(e => e.status === ANALYSIS_STATUS.PARTIAL))
          this.userFailedAnalysis.set(mergedPreviews.filter(e => e.status === ANALYSIS_STATUS.FAILED));
          this.userRunningAnalysis.set(mergedPreviews.filter(e => e.status !== ANALYSIS_STATUS.COMPLETED 
                                                                && e.status !== ANALYSIS_STATUS.FAILED 
                                                                && e.status !== ANALYSIS_STATUS.PARTIAL));
        
          console.log("Refreshing user analysis list: ", mergedPreviews);
        });
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails;
        console.log("Error: ", error);

        this.updateLoadingStatus(false)
      }
    });
  }

  updateLoadingStatus(value: boolean) {
    if (this.userCompletedAnalysis().length <= 0 
    && this.userFailedAnalysis().length <= 0 
    && this.userRunningAnalysis().length <= 0
    && this.userPartialAnalysis().length <= 0) {

      this.loadingCompletedAnalysis.set(value)
      this.loadingFailedAnalysis.set(value)
      this.loadingRunningAnalysis.set(value)
      this.loadingPartialAnalysis.set(value)
    }
  }
}
