import { Component, Input } from '@angular/core';
import { AnalysisPreview } from '../../types/analysis.type';
import { DatePipe, NgClass, UpperCasePipe } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { Router } from '@angular/router';
import { LoadingData } from "../loading-data/loading-data";
import { LoadingIcon } from "../loading-icon/loading-icon";
import { getScoreColor } from '../../constants/analysis-score-color';

@Component({
  selector: 'app-analysis-completed',
  imports: [DatePipe, NgClass, UpperCasePipe, RemoveUnderscorePipe, LoadingData, LoadingIcon],
  templateUrl: './analysis-completed.html',
  styleUrl: './analysis-completed.css'
})
export class AnalysisCompleted {

  @Input() analysisPreview!: AnalysisPreview | undefined

  getScoreColor = getScoreColor

  constructor(private router: Router) {}

  navigateToAnalysisDeatailsPage() {
    if (this.analysisPreview != null) {
      this.router.navigate(['/history/details', this.analysisPreview.id])
    }
  }

}
