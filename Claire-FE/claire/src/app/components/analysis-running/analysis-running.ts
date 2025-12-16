import { Component, Input } from '@angular/core';
import { AnalysisPreview } from '../../types/analysis.type';
import { DatePipe, UpperCasePipe } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { LoadingData } from "../loading-data/loading-data";
import { LoadingIcon } from "../loading-icon/loading-icon";
import { Router } from '@angular/router';

@Component({
  selector: 'app-analysis-running',
  imports: [DatePipe, UpperCasePipe, RemoveUnderscorePipe, LoadingData, LoadingIcon],
  templateUrl: './analysis-running.html',
  styleUrl: './analysis-running.css'
})
export class AnalysisRunning {

  @Input() analysisPreview!: AnalysisPreview | undefined

  constructor(private router: Router) {}

  navigateToAnalysisDeatailsPage() {
    if (this.analysisPreview != null) {
      this.router.navigate(['/history/details', this.analysisPreview.id])
    }
  }

}
