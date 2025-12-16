import { Component, Input } from '@angular/core';
import { AIPreview, GoogleAiJsonExtra } from '../../types/tool.type';
import { LoadingData } from "../loading-data/loading-data";
import { LoadingIcon } from "../loading-icon/loading-icon";
import { TitleCasePipe } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-ai-model-card',
  imports: [LoadingData, LoadingIcon, TitleCasePipe, RemoveUnderscorePipe, DecimalPipe],
  templateUrl: './ai-model-card.html',
  styleUrl: './ai-model-card.css'
})
export class AiModelCard {

  @Input() aiTool?: AIPreview | undefined
  @Input() providerSection?: Boolean | undefined

  getGoogleExtraDetails(): GoogleAiJsonExtra | null {

    if (!this.aiTool) 
      return null

    if (!this.aiTool.provider_name) 
      return null

    if (!this.aiTool.ai_json_extra) 
      return null;

    try {
      return JSON.parse(this.aiTool.ai_json_extra) as GoogleAiJsonExtra;
    } catch (e) {
      console.error("Invalid JSON content", e);
      return null;
    }
  }

}
