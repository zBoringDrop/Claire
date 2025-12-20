import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {

  protected readonly THEME_KEY = 'THEME';

  readonly THEMES: string[] = [
    'theme-soma-light',
    'theme-soma-classic'
  ];

  constructor() {
    this.initialize();
  }

  initialize() {
    const savedTheme = this.get();

    if (savedTheme) {
      console.log('Loaded theme from localStorage:', savedTheme);
      this.applyThemeToHtml(savedTheme);
    } else {
      console.log('No theme saved, applying default');
      this.set(0);
    }
  }

  get(): string | null {
    return localStorage.getItem(this.THEME_KEY);
  }

  set(id: number) {
    if (id >= 0 && id < this.THEMES.length) {
      const themeName = this.THEMES[id];
      console.log('Selected theme:', themeName);

      localStorage.setItem(this.THEME_KEY, themeName);
      this.applyThemeToHtml(themeName);
    } else {
      console.error('Invalid theme id:', id);
    }
  }

  private applyThemeToHtml(themeName: string) {
    const html = document.documentElement;

    this.THEMES.forEach(theme => {
      html.classList.remove(theme);
    });

    html.classList.add(themeName);
  }

  getCurrentTheme(): string | null {
    return this.get();
  }
}