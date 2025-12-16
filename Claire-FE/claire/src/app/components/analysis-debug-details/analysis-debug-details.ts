import { Component, Input, signal } from '@angular/core';
import { AnalysisSchema } from '../../types/analysis.type';
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-analysis-debug-details',
  imports: [JsonPipe],
  templateUrl: './analysis-debug-details.html',
  styleUrl: './analysis-debug-details.css'
})
export class AnalysisDebugDetails {

  @Input() analysis: AnalysisSchema | undefined

  completeJsonResponseStatus = signal<boolean>(true)

  toggleCompleteJsonResponse() {
    this.completeJsonResponseStatus.update(res => !res)
  }

}
