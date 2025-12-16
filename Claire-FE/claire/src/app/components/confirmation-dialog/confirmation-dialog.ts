import { CommonModule } from '@angular/common';
import { Component, Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-confirmation-dialog',
  imports: [CommonModule],
  templateUrl: './confirmation-dialog.html',
  styleUrl: './confirmation-dialog.css'
})
export class ConfirmationDialog {

  @Input() idToRemove: number | undefined
  @Input() resource: string | undefined
  @Output() dialogClose = new EventEmitter<'confirm' | 'cancel'>()

  confirm() {
    this.dialogClose.emit('confirm')
  }

  cancel() {
    this.dialogClose.emit('cancel')
  }

}
