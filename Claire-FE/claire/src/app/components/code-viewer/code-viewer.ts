import { Component, Input } from '@angular/core';
import { CodeHighlightDirective } from "../../utilities/code-highlight.directive";
import { toast } from 'ngx-sonner';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-code-viewer',
  imports: [CodeHighlightDirective, NgClass],
  templateUrl: './code-viewer.html',
  styleUrl: './code-viewer.css',
})
export class CodeViewer {

  @Input() code: string | undefined
  @Input() onlyCode: boolean | undefined

  async copyToClipboard() {
    if (!this.code) {
      console.log("No code to copy");
      return;
    }

    try {
      await navigator.clipboard.writeText(this.code);
      console.log('Text copied to clipboard successfully');
      toast.info('Code copied')
    } catch (error) {
      console.error('Unable to copy text to clipboard: ', error);
    }
  }

}
