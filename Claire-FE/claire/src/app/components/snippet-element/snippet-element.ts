import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CodeSnippetPreview } from '../../types/codesnippet.type';
import { DatePipe } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { TitleCasePipe } from '@angular/common';
import { LoadingData } from "../loading-data/loading-data";
import { Router } from '@angular/router';

@Component({
  selector: 'app-snippet-element',
  imports: [DatePipe, RemoveUnderscorePipe, TitleCasePipe, LoadingData],
  templateUrl: './snippet-element.html',
  styleUrl: './snippet-element.css',
})
export class SnippetElement {

  @Input() snippet: CodeSnippetPreview | null | undefined
  @Output() snippetDeleteClick = new EventEmitter<number>()

  constructor(private router: Router) {}

  sendRequest() {
    if (this.snippet != null) {
      console.log("Snippet id to delete emitted: ", this.snippet.id)
      this.snippetDeleteClick.emit(this.snippet.id)
    }
  }

  navigateToSnippetDetails() {
    if (this.snippet != null) {
      this.router.navigate(['/sources/snippet', this.snippet.id])
    }
  }

}
