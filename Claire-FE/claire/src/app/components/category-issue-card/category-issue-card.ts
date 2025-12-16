import { Component, ElementRef, Input, signal } from '@angular/core';
import { NgClass } from '@angular/common';
import { AnalysisIssue } from '../../types/analysis.type';
import { getScoreColor } from '../../constants/analysis-score-color';
import { CodeViewer } from "../code-viewer/code-viewer";

@Component({
  selector: 'app-category-issue-card',
  imports: [NgClass, CodeViewer],
  templateUrl: './category-issue-card.html',
  styleUrl: './category-issue-card.css'
})
export class CategoryIssueCard {
  getScoreColor = getScoreColor;
  detailsSectionStatus = signal<boolean>(false);

  @Input() i!: number;
  @Input() issue!: AnalysisIssue;

  constructor(private el: ElementRef) {}

  openDetails() {
    this.detailsSectionStatus.update(val => !val);
  }

}