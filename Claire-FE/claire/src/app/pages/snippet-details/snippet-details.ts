import { Component, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { ResourceNotFound } from "../../components/resource-not-found/resource-not-found";
import { getNotFoundCategoryByType, NotFoundCategory, NotFoundCategoryEnum } from '../../constants/not-found-category';
import { ProgramminglanguageService } from '../../services/programminglanguage-service';
import { ProgrammingLanguage } from '../../types/programminglanguage.type';
import { AnalysisService } from '../../services/analysis-service';
import { AnalysisPreview } from '../../types/analysis.type';
import { TitleCasePipe } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { DatePipe } from '@angular/common';
import { LoadingData } from "../../components/loading-data/loading-data";
import { ANALYSIS_STATUS } from '../../constants/analysis-status';
import { AnalysisCompleted } from "../../components/analysis-completed/analysis-completed";
import { AnalysisRunning } from "../../components/analysis-running/analysis-running";
import { AnalysisFailed } from "../../components/analysis-failed/analysis-failed";
import { ConfirmationDialog } from "../../components/confirmation-dialog/confirmation-dialog";
import { CodeViewer } from "../../components/code-viewer/code-viewer";
import { CodeSnippet } from '../../types/codesnippet.type';
import { CodesnippetService } from '../../services/codesnippet-service';
import { toast } from 'ngx-sonner';

@Component({
  selector: 'app-snippet-details',
  imports: [ResourceNotFound, TitleCasePipe, RemoveUnderscorePipe, DatePipe, LoadingData, AnalysisCompleted, AnalysisRunning, AnalysisFailed, ConfirmationDialog, CodeViewer],
  templateUrl: './snippet-details.html',
  styleUrl: './snippet-details.css',
})
export class SnippetDetails {
constructor(private router: Router,
            private activatedRoute: ActivatedRoute,
            private snippetService: CodesnippetService,
            private programmingLanguageService: ProgramminglanguageService,
            private analysisService: AnalysisService) {}

  notFoundCategory: NotFoundCategory | undefined = getNotFoundCategoryByType(NotFoundCategoryEnum.snippetId)

  showDeleteDialog = signal<boolean>(false)

  snippetId: number | undefined
  snippet = signal<CodeSnippet | undefined>(undefined)
  language = signal<ProgrammingLanguage | undefined>(undefined)
  
  analysis = signal<AnalysisPreview[]>([])
  protected readonly ANALYSIS_STATUS = ANALYSIS_STATUS

  notFound = signal<boolean>(false)
  
  ngOnInit() {
    this.readIdParameter()
  }

  readIdParameter() {
    this.snippetId = Number(this.activatedRoute.snapshot.paramMap.get('id'))

    if (this.snippetId) {
      this.findSnippet()
    } else {
      this.notFound.set(true)
    }
  }

  findSnippet() {
    if (!this.snippetId) {
      return
    }

    this.snippetService.getById(this.snippetId).subscribe({
      next: (res: CodeSnippet) => {
        console.log("Received snippet: ", res)
        this.snippet.set(res)

        this.findProgrammingLanguage(res.programming_language_id)
        this.findAnalysis(res.id)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting snippet: ", error)
        this.notFound.set(true)
      }
    })
  }

  findProgrammingLanguage(languageId: number) {
    this.programmingLanguageService.getById(languageId).subscribe({
      next: (res: ProgrammingLanguage) => {
        console.log("Received file language: ", res)
        this.language.set(res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting file language: ", error)
      }
    })
  }

  findAnalysis(snippetId: number) {
    this.analysisService.getPreviewsByUserAndSnippetId(snippetId).subscribe({
      next: (res: AnalysisPreview[]) => {
        console.log("Received snippet analysis previews: ", res)
        this.orderResultsByDate(res)
        this.analysis.set(res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting snippet analysis previews: ", error)
      }
    })
  }

  orderResultsByDate(res: AnalysisPreview[]) {
    res.sort((a, b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime())
  }

  navigateToSources() {
    this.router.navigate(['/sources'])
  }

  openDeleteAnalysisDialog() {
    this.showDeleteDialog.set(true)
    document.body.style.overflow = 'hidden';
  }

  closeDeleteAnalysisDialog() {
    this.showDeleteDialog.set(false)
    document.body.style.overflow = '';
  }

  onDialogResponse(res: 'confirm' | 'cancel') {
    this.closeDeleteAnalysisDialog()

    if (res === 'confirm') {
      this.deleteSnippet()
    }
  }

  deleteSnippet() {
    if (!this.snippet()) {
      return
    }

    this.snippetService.delete(this.snippet()!.id).subscribe({
      next: (res: CodeSnippet) => {
        console.log("Deleted snippet: ", res)
        
        this.router.navigate(['/sources'])
        toast.info('Snippet deleted')
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error deleting snippet: ", error)
      }
    })
  }
}
