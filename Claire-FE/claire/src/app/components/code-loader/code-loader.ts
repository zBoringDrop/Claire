import { Component, Input, signal } from '@angular/core';
import { NgClass } from '@angular/common';
import { CodesnippetService } from '../../services/codesnippet-service';
import { FileService } from '../../services/file-service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CodeSnippetPreview } from '../../types/codesnippet.type';
import { FileCodePreview } from '../../types/file.type';
import { DatePipe } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { TitleCasePipe } from '@angular/common';
import { SnippetUploader } from "../snippet-uploader/snippet-uploader";
import { FileUploader } from "../file-uploader/file-uploader";
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';

@Component({
  selector: 'app-code-loader',
  imports: [NgClass, ReactiveFormsModule, DatePipe, RemoveUnderscorePipe, TitleCasePipe, SnippetUploader, FileUploader],
  templateUrl: './code-loader.html',
  styleUrl: './code-loader.css'
})
export class CodeLoader {

  @Input() source_id!: FormControl<string>
  @Input() source_title!: FormControl<string>
  @Input() source_isSnippet!: FormControl<boolean | null>

  
  libraryFilesList = signal<FileCodePreview[]>([])
  librarySnippetsList = signal<CodeSnippetPreview[]>([])

  constructor(private codesnippetService: CodesnippetService, 
              private fileService: FileService) {}

  loaderOptions = [
    {title: 'Upload new', icon: 'fi-rr-cloud-upload-alt', clicked: false},
    {title: 'Paste code', icon: 'fi-rr-paste text-sm', clicked: true},
    {title: 'Library', icon: 'fi-rr-folder-open', clicked: false}
  ]

  disableAll() {
    for (let option of this.loaderOptions) {
      option.clicked = false
    }
  }

  enableByTitle(title: string) {
    this.disableAll()
    for (let option of this.loaderOptions) {
      if (option.title === title) {
        option.clicked = true
        return
      }
    }
  }

  ngOnInit() {
    this.fillLibraryFiles()
    this.fillLibrarySnippets()
  }

  fillLibraryFiles() {
    this.fileService.listAllUserFilePreviews().subscribe({
      next: (res: FileCodePreview[]) => {
        console.log(res)
        this.orderFileByDate(res)
        this.libraryFilesList.set(res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on getting user files: ", error)
      }
    })
  }

  fillLibrarySnippets() {
    this.codesnippetService.listUserCodeSnippetsPreview().subscribe({
      next: (res: CodeSnippetPreview[]) => {
        console.log(res)
        this.orderSnippetByDate(res)
        this.librarySnippetsList.set(res)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on getting user snippets: ", error)
      }
    })
  }

  orderFileByDate(files: FileCodePreview[]) {
    files.sort((a, b) => new Date(b.upload_datetime).getTime() - new Date(a.upload_datetime).getTime())
  }

  orderSnippetByDate(snippets: CodeSnippetPreview[]) {
    snippets.sort((a, b) => new Date(b.creation_datetime).getTime() - new Date(a.creation_datetime).getTime())
  }

  setSelectedFile(source: FileCodePreview) {
    this.source_id.setValue(source.id + "")
    this.source_title.setValue(source.filename)
    this.source_isSnippet.setValue(false)
  }

  setSelectedSnippet(source: CodeSnippetPreview) {
    this.source_id.setValue(source.id + "")
    this.source_title.setValue(source.title)
    this.source_isSnippet.setValue(true)
  }

  onFileChange(fileIdAndName: { id: number, name: string }) {
    this.source_id.setValue(fileIdAndName.id + "")
    this.source_title.setValue(fileIdAndName.name)
    this.source_isSnippet.setValue(false)

    this.fillLibraryFiles()
    this.fillLibrarySnippets()
  }

  onSnippetChange(SnippetIdAndName: { id: number, name: string }) {
    this.source_id.setValue(SnippetIdAndName.id + "")
    this.source_title.setValue(SnippetIdAndName.name)
    this.source_isSnippet.setValue(true)

    this.fillLibraryFiles()
    this.fillLibrarySnippets()
  }


}
