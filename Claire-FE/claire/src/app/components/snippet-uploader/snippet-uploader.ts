import { Component, EventEmitter, Output, signal } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CodeSnippet } from '../../types/codesnippet.type';
import { CodesnippetService } from '../../services/codesnippet-service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { ReactiveFormsModule } from '@angular/forms';
import { ProgrammingLanguageSelector } from "../programming-language-selector/programming-language-selector";

@Component({
  selector: 'app-snippet-uploader',
  imports: [ReactiveFormsModule, ProgrammingLanguageSelector],
  templateUrl: './snippet-uploader.html',
  styleUrl: './snippet-uploader.css',
})
export class SnippetUploader {

  isUploading = signal<boolean>(false)

  @Output() snippetIdAndName = new EventEmitter<{id: number, name: string}>()

  constructor(private codesnippetService: CodesnippetService) {}

  uploadCodeSnippetForm = new FormGroup({
    title: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(30)]),
    code_text: new FormControl('', [Validators.required, Validators.minLength(20), Validators.maxLength(2500)]),
    programming_language_id: new FormControl<number | null>(null, [Validators.required])
  })

  get code_text() {
    return this.uploadCodeSnippetForm.get('code_text')
  }

  get title() {
    return this.uploadCodeSnippetForm.get('title')
  }

  get programming_language_id() {
    return this.uploadCodeSnippetForm.get('programming_language_id')
  }

  uploadCodeSnippet() {

    this.isUploading.set(true)

    if (this.uploadCodeSnippetForm.invalid) {
      console.log("Invalid code snippet form data")
      this.isUploading.set(false)
      return
    }

    const codeToUpload: CodeSnippet = this.uploadCodeSnippetForm.value as CodeSnippet
    console.log('Code: ', codeToUpload)

    this.codesnippetService.uploadNew(codeToUpload).subscribe({
      next: (res: CodeSnippet) => {
        this.snippetIdAndName.emit({id: res.id, name: res.title})
        
        console.log("Res: ", res)
        this.uploadCodeSnippetForm.reset()
        this.isUploading.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error: ", error)
        this.isUploading.set(false)
      }
      
    })
  }

  onProgrammingLanguageSelected(id: number) {
    this.programming_language_id?.setValue(id)
    console.log("Programming language id received from the select: ", id);
  }

}
