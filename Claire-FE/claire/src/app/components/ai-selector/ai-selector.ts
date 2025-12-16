import { Component, Input, signal } from '@angular/core';
import { AiService } from '../../services/ai-service';
import { AIPreview } from '../../types/tool.type';
import { FormsModule, ReactiveFormsModule, FormControl} from "@angular/forms";
import { AiModelCard } from "../ai-model-card/ai-model-card";
import { RouterLink } from '@angular/router';
import { AimodelsyncService } from '../../services/aimodelsync-service';
import { GenericResponse } from '../../types/genericresponse.type';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';

@Component({
  selector: 'app-ai-selector',
  imports: [FormsModule, ReactiveFormsModule, AiModelCard, RouterLink],
  templateUrl: './ai-selector.html',
  styleUrl: './ai-selector.css'
})
export class AiSelector {

  isLoading = signal<boolean>(false)

  aiToolsSearch = signal<AIPreview[]>([])
  aiTools = signal<AIPreview[]>([])

  searchModelInput = new FormControl('')

  @Input() toolIdForm!: FormControl<string>;
  @Input() tool_name!: FormControl<string>;
  
  constructor(private aiService: AiService,
              private aimodelsyncService: AimodelsyncService
  ) {}

  ngOnInit() {
    this.fillList()

    this.searchModelInput.valueChanges.subscribe(v => {
      if (!v) {
        this.aiToolsSearch.set(this.aiTools());
        return;
      }

      const value = String(v).toLowerCase();

      const res = this.aiTools().filter(model =>
        model.model.toLowerCase().includes(value) ||
        model.name.toLowerCase().includes(value) ||
        model.provider_name.toLowerCase().includes(value) ||
        model.provider_type.toLowerCase().includes(value)
      );

      this.aiToolsSearch.set(res);
    });

    this.toolIdForm.valueChanges.subscribe(selectedAIId => {
      const selectedAI = this.aiTools().find(t => String(t.ai_id) === String(selectedAIId));
      if (selectedAI) {
        this.tool_name.setValue(selectedAI.name);
        console.log('Selected:', selectedAI.name);
        console.log('ID: ', selectedAIId)
      } else {
        console.warn('No AI id found for:', selectedAIId);
      }
    });
  }

  fillList() {
    this.isLoading.set(true)

    this.aiService.getUserAvailableModels().subscribe({
      next: (res: AIPreview[]) => {
        this.sortByModel(res)

        this.aiTools.set(res)
        this.aiToolsSearch.set(res)
        console.log("AI Tools: ", this.aiTools())

        this.isLoading.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on getting AI Models: ", error)
        this.isLoading.set(false)
      }
    })
  }

  resetSearchInput() {
    this.searchModelInput.reset()
  }

  resyncModels() {
    this.resetSearchInput()

    this.aimodelsyncService.syncAllModels().subscribe({
      next: (res: GenericResponse) => {
        console.log("Resync model request status: ", res)

        this.fillList()
      },
      error: (error: HttpErrorResponse) => {
        const err = error.error as ProblemDetails
        console.log("Error on resync models: ", err)
      }
    })
  }

  sortByModel(res: AIPreview[]) {
    res.sort((a, b) => a.model.localeCompare(b.model))
  }

}
