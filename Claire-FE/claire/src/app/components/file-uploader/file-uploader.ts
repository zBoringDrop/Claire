import { Component, EventEmitter, Output, signal } from '@angular/core';
import { FileService } from '../../services/file-service';
import { ProblemDetails } from '../../types/problemdetails';
import { HttpErrorResponse } from '@angular/common/http';
import { FileCode } from '../../types/file.type';

@Component({
  selector: 'app-file-uploader',
  imports: [],
  templateUrl: './file-uploader.html',
  styleUrl: './file-uploader.css',
})
export class FileUploader {

  isUploading = signal<boolean>(false)

  constructor(private fileService: FileService) {}

  @Output() fileIdAndName = new EventEmitter<{id: number, name: string}>()

  selectedFile?: File

  onFileSelected(event: any) {
    const input = event.target.files[0] as File

    console.log('Uploaded file: ', input)
    if (input.size > 0) {
      this.selectedFile = input
    }
    
  }

  uploadFile() {
    this.isUploading.set(true)

    if (!this.selectedFile) {
      console.log("You have to select a file!")
      this.isUploading.set(false)
      return
    }

    const formData = new FormData()
    formData.append('uploadedFile', this.selectedFile)

    this.fileService.createNew(formData).subscribe({
      next: (res: FileCode) => {
        this.fileIdAndName.emit({id: res.id, name: res.filename})

        console.log("New uploaded file: ", res)
        this.selectedFile = undefined
        this.isUploading.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error: ", error)
        this.isUploading.set(false)
      }
    })
  }

}
