import { Component, signal } from '@angular/core';
import { ThemeService } from '../../services/theme-service';
import { RemoveHyphenPipePipe } from '../../pipes/remove-hyphen-pipe-pipe';
import { TitleCasePipe } from '@angular/common';

@Component({
  selector: 'app-theme-selector',
  imports: [RemoveHyphenPipePipe, TitleCasePipe],
  templateUrl: './theme-selector.html',
  styleUrl: './theme-selector.css',
})
export class ThemeSelector {

  constructor(private themeService: ThemeService) {}

  themesList = signal<string[]>([])
  actualTheme = signal<string>('')

  ngOnInit() {
    const aTheme = this.themeService.getCurrentTheme()
    if (aTheme) {
      this.actualTheme.set(aTheme)
    } else {
      this.actualTheme.set(this.themeService.THEMES[0])
    }
    
    this.themesList.set(this.themeService.THEMES)
  }

  onThemeChange(event: any) {
    const themeId: number = Number(event.target.value)
    console.log('Theme selected: ', themeId)
    this.themeService.set(themeId)
  }

}
