import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { ProgrammingLanguage } from '../../types/programminglanguage.type';
import { ProgramminglanguageService } from '../../services/programminglanguage-service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { TitleCasePipe } from '@angular/common';
import { RemoveUnderscorePipe } from '../../pipes/remove-underscore-pipe';

@Component({
  selector: 'app-programming-language-selector',
  imports: [TitleCasePipe, RemoveUnderscorePipe],
  templateUrl: './programming-language-selector.html',
  styleUrl: './programming-language-selector.css',
})
export class ProgrammingLanguageSelector {

  programmingLanguagesList = signal<ProgrammingLanguage[]>([])

  @Input() languageInfo: string | null | undefined;

  @Output() programmingLanguageId = new EventEmitter<number>

  constructor(private programmingLanguageService: ProgramminglanguageService) {}

  ngOnInit() {
    this.fillList()
  }

  get normalizedLanguageInfo(): string {
    return this.languageInfo?.trim().toLowerCase() ?? '';
  }

  fillList() {
    this.programmingLanguageService.getAll().subscribe({
      next: (res: ProgrammingLanguage[]) => {
        this.orderByName(res);
        this.programmingLanguagesList.set(res);

        let defaultLang = res.find(l => 
          this.normalizedLanguageInfo && 
          l.name.toLowerCase().includes(this.normalizedLanguageInfo)
        );

        if (!defaultLang) {
          defaultLang = res.find(l => l.name.toUpperCase().includes('PSEUDO'));
        }

        if (defaultLang) {
           console.log("Initial language selected:", defaultLang.name);
           this.programmingLanguageId.emit(defaultLang.id); 
        }
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails;
        console.log("Error on getting all programmingLanguages: ", error);
      }
    });
  }

  orderByName(list: ProgrammingLanguage[]) {
    list.sort((a, b) => (a.name < b.name ? -1 : 1));
  }

  onLanguageChange(event: Event) {
    const select = event.target as HTMLSelectElement;
    const value = select.value;

    console.log('Selected programming language ID: ', value);
    this.programmingLanguageId.emit(Number(value))
  }


}
