import { Component, signal } from '@angular/core';
import { FileUploader } from "../../components/file-uploader/file-uploader";
import { SnippetUploader } from "../../components/snippet-uploader/snippet-uploader";
import { NgClass } from '@angular/common';
import { FileService } from '../../services/file-service';
import { CodeSnippet, CodeSnippetPreview } from '../../types/codesnippet.type';
import { FileCode, FileCodePreview } from '../../types/file.type';
import { CodesnippetService } from '../../services/codesnippet-service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { FileElement } from "../../components/file-element/file-element";
import { SnippetElement } from "../../components/snippet-element/snippet-element";
import { ConfirmationDialog } from "../../components/confirmation-dialog/confirmation-dialog";

@Component({
  selector: 'app-sources',
  imports: [FileUploader, SnippetUploader, NgClass, FileElement, SnippetElement, ConfirmationDialog],
  templateUrl: './sources.html',
  styleUrl: './sources.css',
})
export class Sources {

  libraryFilesList = signal<FileCodePreview[]>([])
  librarySnippetsList = signal<CodeSnippetPreview[]>([])

  showDeleteDialog = signal<boolean>(false)
  sourceIdToDelete = signal<number>(-1)
  sourceNameToDelete = signal<string>("")

  constructor(private fileService: FileService,
              private snippetService: CodesnippetService
  ) {}

  ngOnInit() {
    this.fillLibraryFiles()
    this.fillLibrarySnippets()
  }

  loaderOptions = [
    {title: 'Files', icon: 'fi-rr-cloud-upload-alt', clicked: false},
    {title: 'Code snippets', icon: 'fi-rr-paste text-sm', clicked: true},
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
    this.snippetService.listUserCodeSnippetsPreview().subscribe({
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

  onUploadFile() {
    console.log("New file uploaded, reload files list")
    this.fillLibraryFiles()
  }

  onUploadSnippet() {
    console.log("New snippet uploaded, reload snippets list")
    this.fillLibrarySnippets()
  }

  onFileDelete(idToDelete: number) {
    console.log("Delete file ", idToDelete, "?")
    this.sourceIdToDelete.set(idToDelete)
    this.sourceNameToDelete.set("file")
    this.openDeleteAnalysisDialog()
  }

  onSnippetDelete(idToDelete: number) {
    console.log("Delete snippet ", idToDelete, "?")
    this.sourceIdToDelete.set(idToDelete)
    this.sourceNameToDelete.set("snippet")
    this.openDeleteAnalysisDialog()
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
      if (this.sourceNameToDelete() === 'file') {
        this.deleteFile(this.sourceIdToDelete())
      } else if (this.sourceNameToDelete() === 'snippet') {
        this.deleteSnippet(this.sourceIdToDelete())
      }
    } else {
      console.log("No source to delete")
    }
  }

  deleteFile(fileId: number) {
    this.fileService.delete(fileId).subscribe({
      next: (res: FileCode) => {
        console.log("File deleted correctly: ", res)
        this.fillLibraryFiles()
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on deleting file: ", error)
      }
    })
  }

  deleteSnippet(snippetId: number) {
    this.snippetService.delete(snippetId).subscribe({
      next: (res: CodeSnippet) => {
        console.log("Snippet deleted correctly: ", res)
        this.fillLibrarySnippets()
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error on deleting snippet: ", error)
      }
    })
  }
  

}
