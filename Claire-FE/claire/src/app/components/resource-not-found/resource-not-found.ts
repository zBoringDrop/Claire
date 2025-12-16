import { Component, Input } from '@angular/core';
import { RouterLink } from "@angular/router";
import { NotFoundCategory } from '../../constants/not-found-category';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-resource-not-found',
  imports: [RouterLink, NgClass],
  templateUrl: './resource-not-found.html',
  styleUrl: './resource-not-found.css'
})
export class ResourceNotFound {

  @Input() notFoundCategory: NotFoundCategory | undefined
  @Input() id: number | undefined

}
