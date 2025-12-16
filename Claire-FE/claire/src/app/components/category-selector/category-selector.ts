import { Component, Input, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormControl, FormArray} from "@angular/forms";
import { Category } from '../../types/category.type';
import { CategoryService } from '../../services/category-service';
import { getDefaultCategory } from '../../constants/default-categories';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';
import { TitleCasePipe, NgClass } from '@angular/common';
import { CategoryCard } from "../category-card/category-card";
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';

@Component({
  selector: 'app-category-selector',
  imports: [FormsModule, ReactiveFormsModule, RemoveUnderscorePipe, TitleCasePipe, NgClass, CategoryCard],
  templateUrl: './category-selector.html',
  styleUrl: './category-selector.css'
})
export class CategorySelector {

  isLoading = signal<boolean>(false)

  categoriesList = signal<Category[]>([])

  @Input() categoryIdsForm!: FormArray;
  @Input() analysis_category_ids!: FormControl<string[]>
  @Input() analysis_categories_name!: FormControl<string[]>

  getDefaultCategory = getDefaultCategory

  constructor(private categoryService: CategoryService) {}

  ngOnInit() {
    this.fillList()

    this.categoryIdsForm.valueChanges.subscribe(_ => {
      const selectedCats = this.getSelectedCategories()

      const ids = this.getSelectedCategoryIds(selectedCats);
      const names = this.getSelectedCategoryNames(selectedCats)

      this.analysis_category_ids.setValue(ids)
      this.analysis_categories_name.setValue(names)
                    
      console.log("Selected categories ids:", ids)
      console.log("Selected categories names:", names)
    });
  }

  fillList() {
    this.isLoading.set(true)

    this.categoryService.getAllCategories().subscribe({
      next: (res: Category[]) => {
        this.sortByCategoryTypeName(res)
        
        console.log(res)
        this.categoriesList.set(res)
        this.buildCheckBoxArray()
        this.isLoading.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error getting categories: ", error)
        this.isLoading.set(false)
      }
    })
  }

  buildCheckBoxArray() {
    const formArray = this.categoryIdsForm as FormArray
    this.categoriesList().forEach(_ => {
      formArray.push(new FormControl(false))
    })
  }

  getSelectedCategories(): Category[] {
    const raw = this.categoryIdsForm.value as boolean[];

    return raw
      .map((checked, i) => checked ? this.categoriesList()[i] : null)
      .filter(v => v !== null);
  }

  getSelectedCategoryNames(categories: Category[]): string[] {
    return categories.reduce<string[]>((acc, category) => {
      acc.push(category.type + "");
      return acc;
    }, []);
  }

  getSelectedCategoryIds(categories: Category[]): string[] {
    return categories.reduce<string[]>((acc, category) => {
      acc.push(category.id + "");
      return acc;
    }, []);
  }

  asControl(i: number): FormControl {
    return this.categoryIdsForm.at(i) as FormControl;
  }

  sortByCategoryTypeName(res: Category[]) {
    res.sort((a, b) => a.type.localeCompare(b.type))
  }

}
