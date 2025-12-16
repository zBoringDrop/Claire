import { Component, signal } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AiModelCard } from "../../components/ai-model-card/ai-model-card";
import { AIPreview } from '../../types/tool.type';
import { FileCodePreview } from '../../types/file.type';
import { CategoryIssueCard } from '../../components/category-issue-card/category-issue-card';
import { AnalysisSchema } from '../../types/analysis.type';
import { CodeSnippetPreview } from '../../types/codesnippet.type';
import { AnalysisIdCategoryName } from '../../types/category.type';
import { getDefaultCategory } from '../../constants/default-categories';
import { AnalysisService } from '../../services/analysis-service';
import { FileService } from '../../services/file-service';
import { CodesnippetService } from '../../services/codesnippet-service';
import { AiService } from '../../services/ai-service';
import { CategoryService } from '../../services/category-service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { NgClass } from '@angular/common';
import { DatePipe } from '@angular/common';
import { TitleCasePipe } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { ResourceNotFound } from "../../components/resource-not-found/resource-not-found";
import { NotFoundCategory, NotFoundCategoryEnum, getNotFoundCategoryByType } from '../../constants/not-found-category';
import { LoadingData } from "../../components/loading-data/loading-data";
import { CategoryAnalysisCard } from "../../components/category-analysis-card/category-analysis-card";
import { ANALYSIS_STATUS, inProgress, notRunning } from '../../constants/analysis-status';
import { Subject, takeUntil, timer } from 'rxjs';
import { ConfirmationDialog } from "../../components/confirmation-dialog/confirmation-dialog";
import { AnalysisDebugDetails } from "../../components/analysis-debug-details/analysis-debug-details";
import { getStatusConfig } from '../../constants/analysis-status';
import { OllamaUsedConfig } from "../../components/ollama-used-config/ollama-used-config";
import { CodeViewer } from "../../components/code-viewer/code-viewer";
import { AnalysisRequest } from '../../types/analysis.type';
import { toast } from 'ngx-sonner';

@Component({
  selector: 'app-analysis-details',
  imports: [AiModelCard, CategoryIssueCard, NgClass, DatePipe, TitleCasePipe, RemoveUnderscorePipe, ResourceNotFound, LoadingData, CategoryAnalysisCard, ConfirmationDialog, AnalysisDebugDetails, OllamaUsedConfig, CodeViewer],
  templateUrl: './analysis-details.html',
  styleUrl: './analysis-details.css'
})
export class AnalysisDetails {

  ANALYSIS_STATUS = ANALYSIS_STATUS
  getStatusConfig = getStatusConfig
  getNotFoundCategoryByType = getNotFoundCategoryByType
  inProgress = inProgress;
  notFoundCategory: NotFoundCategory | undefined = getNotFoundCategoryByType(NotFoundCategoryEnum.analysisId)
  
  showDeleteDialog = signal<boolean>(false)

  private destroy$ = new Subject<void>();
  private pollingStarted = false;

  analysisId: number = 0

  notFound = signal<boolean>(false)

  analysis = signal<AnalysisSchema | undefined>(undefined)
  file = signal<FileCodePreview | undefined>(undefined)
  snippet = signal<CodeSnippetPreview | undefined>(undefined)
  tool = signal<AIPreview | undefined>(undefined)
  categories = signal<AnalysisIdCategoryName[]>([])

  selectedCategoryIndex = signal<number>(-1)

  getDefaultCategory = getDefaultCategory

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private analysisService: AnalysisService,
              private fileService: FileService,
              private snippetService: CodesnippetService,
              private toolService: AiService,
              private categoryService: CategoryService
  ) {}

  ngOnInit() {

    this.activatedRoute.paramMap.subscribe(params => {        
        this.analysisId = Number(params.get('id'));
        this.fillAnalysisInfo(); 
    });
  }

  fillAnalysisInfo() {
    this.analysisService.getById(this.analysisId).subscribe({
      next: (res: AnalysisSchema) => {
        this.analysis.set(res);

        console.log("Analysis:", res);

        if (notRunning(res.status)) {
          this.destroyTimer()
        }

        if (inProgress(res.status) && !this.pollingStarted) {
          console.log("Started polling on this resource until elaboration stops. Status: ", res.status)
          this.pollingStarted = true

          timer(0, 10000)
                .pipe(takeUntil(this.destroy$))
                .subscribe(() => this.fillAnalysisInfo());
        }

        if (res.file_id) {
          this.fillFileInfo();
        } else {
          this.fillSnippetInfo();
        }

        this.fillToolInfo();
        this.fillCategoryInfo();
      },
      error: (err) => {
        console.error("Error loading analysis:", err)
        this.notFound.set(true)
      }
    });
  }

  ngOnDestroy() {
    this.destroyTimer()
    this.destroy$.complete();
  }

  destroyTimer() {
    this.destroy$.next();
    this.pollingStarted = false;
    console.log("Ended polling on this resource because elaboration stopped. Status: ", this.analysis()?.status)
  }

  fillFileInfo() {
    const fileId = this.analysis()!.file_id
    if (!fileId) return
    
    this.fileService.getPreviewById(fileId).subscribe({
      next: (res: FileCodePreview) => {
        this.file.set(res)
        console.log("File: ", res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting File: ", error)
      }
    })
  }

  fillSnippetInfo() {
    const snippetId = this.analysis()!.codesnippet_id
    if (!snippetId) return

    this.snippetService.getPreviewById(snippetId).subscribe({
      next: (res: CodeSnippetPreview) => {
        this.snippet.set(res)
        console.log("Code snippet: ", res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting Code snippet: ", error)
      }
    })
  }

  fillToolInfo() {
    const toolId = this.analysis()!.tool_id
    if (!toolId) return

    this.toolService.getModelpreview(toolId).subscribe({
      next: (res: AIPreview) => {
        this.tool.set(res)
        console.log("AI Preview Tool: ", res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting AI Preview tool: ", error)
      }
    })
  }

  fillCategoryInfo() {
    this.categoryService.getCategoriesByAnalysisId(this.analysis()!.id).subscribe({
      next: (res: AnalysisIdCategoryName[]) => {
        this.categories.set(res)
        console.log("Categories: ", res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting categories: ", error)
      }
    })
  }

  navigateToHistory() {
    this.router.navigate(['/history'])
  }

  navigateToAnalysis(analysisId: number) {
    this.router.navigate(['/history/details/' + analysisId])
  }

  navigateToSource() {
    if (this.file()) {
      this.router.navigate(['/sources/file/' + this.file()?.id])
    } else if (this.snippet()) {
      this.router.navigate(['/sources/snippet/' + this.snippet()?.id])
    }
  }

  openDeleteAnalysisDialog() {
    this.showDeleteDialog.set(true)
    document.body.style.overflow = 'hidden';
  }

  closeDeleteAnalysisDialog() {
    this.showDeleteDialog.set(false)
    document.body.style.overflow = '';
  }

  onDialogResponse(response: 'confirm' | 'cancel') {
    this.closeDeleteAnalysisDialog()

    if (response === 'confirm') {
      this.deleteAnalysis()
    }
  }

  deleteAnalysis() {
    if (this.analysis()?.id == null) {
      console.log("Error on delete: analysis ID is null")
      return
    }

    console.log("Analysis to remove: ", this.analysisId)
    this.analysisService.deleteAnalysisById(this.analysisId).subscribe({
      next: (res: AnalysisSchema) => {
        console.log("Analysis deleted: ", res)
        this.navigateToHistory()
        toast.info('Analysis deleted')
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on delete analysis: ", error)
      }
    })
  }

  loadCategoryAnalysis(index: number) {
    if (this.analysis()?.status == ANALYSIS_STATUS.FAILED) {
      console.log("This analysis is failed, nothing to show")
      return
    }

    if (!this.analysis()?.result_json.results[index].response.issues) {
      console.log("This analysis category is failed, nothing to show")
      return
    }

    this.selectedCategoryIndex.set(index)
    console.log("Selected category with local array id: ", index)
  }

  msToSeconds(valueMs: number): number {
    return valueMs/1000
  }

  reAnalyze() {
    if (!this.analysis()) {
      return
    }

    if (this.analysis()?.codesnippet_id) {
      this.reAnalyzeSnippet()
    } else if (this.analysis()?.file_id) {
      this.reAnalyzeFile()
    }
  }

  reAnalyzeFile() {

    if (!this.file() || !this.tool()) {
      return
    }

    const categoryIds = this.categories().map(category => category.category_id)

    const request: AnalysisRequest = {
      source_id: this.file()!.id,
      tool_id: this.tool()!.ai_id,
      analysis_categories: categoryIds
    }

    console.log("Request a re-analyze of this same file analysis: ", request)

    this.analysisService.analyzeFile(request).subscribe({
      next: (res: AnalysisSchema) => {
        console.log("Analysis on this file started: ", res)
        this.navigateToAnalysis(res.id)
        toast.info('Analysis started')
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on restart analysis: ", error)
        toast.info('Error on starting analysis')
      }
    })
  }

  reAnalyzeSnippet() {

    if (!this.snippet() || !this.tool()) {
      return
    }

    const categoryIds = this.categories().map(category => category.category_id)

    const request: AnalysisRequest = {
      source_id: this.snippet()!.id,
      tool_id: this.tool()!.ai_id,
      analysis_categories: categoryIds
    }

    console.log("Request a re-analyze of this same snippet analysis: ", request)

    this.analysisService.analyzeSnippet(request).subscribe({
      next: (res: AnalysisSchema) => {
        console.log("Analysis on this snippet started: ", res)
        this.navigateToAnalysis(res.id)
        toast.info('Analysis started')
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on restart analysis: ", error)
        toast.info('Error on starting analysis')
      }
    })
  }

}
