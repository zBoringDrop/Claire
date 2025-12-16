import { Component, Input, Output } from '@angular/core';
import { NgClass } from '@angular/common';
import { LoadingData } from '../loading-data/loading-data';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { LoadingIcon } from "../loading-icon/loading-icon";
import { AnalysisResultItem } from '../../types/analysis.type';
import { ANALYSIS_STATUS } from '../../constants/analysis-status';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-category-analysis-card',
  imports: [NgClass, LoadingData, RemoveUnderscorePipe, LoadingIcon],
  templateUrl: './category-analysis-card.html',
  styleUrl: './category-analysis-card.css'
})
export class CategoryAnalysisCard {

  ANALYSIS_STATUS = ANALYSIS_STATUS

  @Input() analysisStatus: string | undefined | null
  @Input() i: number | undefined | null
  @Input() result: AnalysisResultItem | undefined | null
  @Input() defCategory: any
  @Input() selectedCategoryIndex: any

  @Output() loadCategoryAnalysis = new EventEmitter<number>()

  loadCategory() {
    if (this.i == null || this.i < 0) {
      console.log("Json result for category ", this.result?.meta.category, " is ", this.i)
      return
    }

    this.loadCategoryAnalysis.emit(this.i)
  }

}
