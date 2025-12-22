import { Component, inject, signal } from '@angular/core';
import { AnalysisService } from '../../services/analysis-service';
import { AiSelector } from '../../components/ai-selector/ai-selector';
import { CategorySelector } from '../../components/category-selector/category-selector';
import { CodeLoader } from '../../components/code-loader/code-loader';
import { FormControl, FormsModule, FormBuilder, ReactiveFormsModule, Validators, FormArray, FormGroup} from "@angular/forms";
import { AnalysisPreview, AnalysisRequest, AnalysisSchema } from '../../types/analysis.type';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { Subject, takeUntil, timer } from 'rxjs';
import { Router } from '@angular/router';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { toast } from 'ngx-sonner';

@Component({
  selector: 'app-analysis',
  imports: [AiSelector, CategorySelector, CodeLoader, FormsModule, ReactiveFormsModule, RemoveUnderscorePipe],
  templateUrl: './analysis.html',
  styleUrl: './analysis.css'
})
export class Analysis {

  sendingAnalysis = signal<boolean>(false)

  private destroy$ = new Subject<void>();

  private formBuilder = inject(FormBuilder)

  analysisInProgress = signal<AnalysisPreview[]>([])

  constructor(private analysisService: AnalysisService,
              private router: Router
  ) {}

  analysisForm = this.formBuilder.group({
    tool_id: this.formBuilder.nonNullable.control('', [Validators.required]),
    tool_name: this.formBuilder.nonNullable.control('', [Validators.required]),
    analysis_categories: this.formBuilder.array<FormGroup>([]),
    analysis_categories_name: this.formBuilder.nonNullable.control<string[]>([], [Validators.required]),
    analysis_category_ids: this.formBuilder.nonNullable.control<string[]>([], [Validators.required]),
    source_id: this.formBuilder.nonNullable.control('', [Validators.required]),
    source_title: this.formBuilder.nonNullable.control('', [Validators.required]),
    source_isSnippet: this.formBuilder.nonNullable.control(null, [Validators.required]),
    use_cot: this.formBuilder.nonNullable.control(true)
  });

  ngOnInit() {
    timer(0, 10000)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => this.fillAnalysisInProgress());
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  fillAnalysisInProgress() {
    this.analysisService.getRunningAnalysis().subscribe(res => {
      this.analysisInProgress.set(res)
      console.log("Refresh for analysisInProgress: ", res)
    })
  }

  get tool_id(): FormControl<string> {
    return this.analysisForm.get('tool_id') as FormControl<string>;
  }

  get tool_name(): FormControl<string> {
    return this.analysisForm.get('tool_name') as FormControl<string>;
  }

  get analysis_categories(): FormArray {
    return this.analysisForm.get('analysis_categories') as FormArray;
  }

  get analysis_categories_name(): FormControl<string[]> {
    return this.analysisForm.get('analysis_categories_name') as FormControl<string[]>;
  }

  get analysis_category_ids(): FormControl<string[]> {
    return this.analysisForm.get('analysis_category_ids') as FormControl<string[]>;
  }

  get source_id(): FormControl<string> {
    return this.analysisForm.get('source_id') as FormControl<string>;
  }

  get source_title(): FormControl<string> {
    return this.analysisForm.get('source_title') as FormControl<string>;
  }

  get source_isSnippet(): FormControl<boolean | null> {
    return this.analysisForm.get('source_isSnippet') as FormControl<boolean | null>;
  }

  get use_cot(): FormControl<boolean> {
    return this.analysisForm.get('use_cot') as FormControl<boolean>;
  }

  makeAnalysisRequest() {
    this.sendingAnalysis.set(true)

    if (this.analysisForm.invalid) {
      console.log("Invalid analysis specifications")
      this.sendingAnalysis.set(false)
      return
    }

    const analysisRequest: AnalysisRequest = {
      source_id: Number(this.source_id.value),
      tool_id: Number(this.tool_id.value),
      analysis_categories: this.analysis_category_ids.value.map(Number),
      use_cot: Boolean(this.use_cot.value)
    }

    console.log("Analysis request: ", analysisRequest)

    if (this.source_isSnippet.value) {
      this.analyzeSnippet(analysisRequest)
    } else {
      this.analyzeFile(analysisRequest)
    }

    console.log("Analysis request sent: ", analysisRequest)
  }

  analyzeSnippet(request: AnalysisRequest) {
    this.analysisService.analyzeSnippet(request).subscribe({
      next: (res: AnalysisSchema) => {
        console.log("Analysis (snippet): ", res)
        this.analysisForm.reset()
        this.scrollToTop()
        this.navigateToAnalysis(res.id)
        this.sendingAnalysis.set(false)
        toast.info('Analysis started')
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error: ", error)
        
        this.sendingAnalysis.set(false)
        toast.info('Error starting analysis')
      }
    })
  }

  analyzeFile(request: AnalysisRequest) {
    this.analysisService.analyzeFile(request).subscribe({
      next: (res: AnalysisSchema) => {
        console.log("Analysis (file): ", res)
        this.analysisForm.reset()
        this.scrollToTop()
        this.navigateToAnalysis(res.id)
        this.sendingAnalysis.set(false)
        toast.info('Analysis started')
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error: ", error)

        this.sendingAnalysis.set(false)
        toast.info('Error starting analysis')
      }
    })
  }

  scrollToTop() {
      window.scrollTo({ top: 0});
      document.body.scrollTo({ top: 0}); 
      document.documentElement.scrollTo({ top: 0});
  }

  navigateToAnalysisDeatailsPage(analysisId: number) {
    if (analysisId != null) {
      this.router.navigate(['/history/details', analysisId])
    }
  }

  navigateToAnalysis(analysisId: number) {
    this.router.navigate(['/history/details/' + analysisId])
  }

}
