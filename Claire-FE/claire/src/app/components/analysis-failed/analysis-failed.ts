import { Component, Input } from '@angular/core';
import { AnalysisPreview } from '../../types/analysis.type';
import { DatePipe, UpperCasePipe } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { LoadingData } from "../loading-data/loading-data";
import { Router } from '@angular/router';

@Component({
  selector: 'app-analysis-failed',
  imports: [DatePipe, UpperCasePipe, RemoveUnderscorePipe, LoadingData],
  templateUrl: './analysis-failed.html',
  styleUrl: './analysis-failed.css'
})
export class AnalysisFailed {

  @Input() analysisPreview!: AnalysisPreview | undefined

  constructor(private router: Router) {}

  navigateToAnalysisDeatailsPage() {
    if (this.analysisPreview != null) {
      this.router.navigate(['/history/details', this.analysisPreview.id])
    }
  }

}
