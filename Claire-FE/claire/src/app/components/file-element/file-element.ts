import { Component, Input, Output, signal } from '@angular/core';
import { FileCodePreview } from '../../types/file.type';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { TitleCasePipe } from '@angular/common';
import { DatePipe } from '@angular/common';
import { LoadingData } from "../loading-data/loading-data";
import { EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-file-element',
  imports: [RemoveUnderscorePipe, TitleCasePipe, DatePipe, LoadingData],
  templateUrl: './file-element.html',
  styleUrl: './file-element.css',
})
export class FileElement {

  @Input() file: FileCodePreview | null | undefined
  @Output() fileDeleteClick = new EventEmitter<number>();

  constructor(private router: Router) {}

  showDeleteDialog = signal<boolean>(false)

  sendRequest() {
    if (this.file != null) {
      console.log("File id to delete emitted: ", this.file.id)
      this.fileDeleteClick.emit(this.file.id)
    }
  }

  navigateToFileDetails() {
    if (this.file != null) {
      this.router.navigate(['/sources/file', this.file.id])
    }
  }

}
