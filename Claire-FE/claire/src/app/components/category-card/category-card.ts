import { Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { TitleCasePipe } from '@angular/common';
import { LoadingData } from "../loading-data/loading-data";
import { LoadingIcon } from "../loading-icon/loading-icon";

@Component({
  selector: 'app-category-card',
  imports: [NgClass, RemoveUnderscorePipe, TitleCasePipe, LoadingData, LoadingIcon],
  templateUrl: './category-card.html',
  styleUrl: './category-card.css'
})
export class CategoryCard {

  @Input() defCategory: any
  @Input() category: any

}
