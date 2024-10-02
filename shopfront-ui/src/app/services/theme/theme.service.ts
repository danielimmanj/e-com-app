import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private readonly themeKey = 'theme';

  constructor() {
    this.loadTheme();
  }

  private loadTheme(): void {
    const savedTheme = localStorage.getItem(this.themeKey);
    if (savedTheme === 'dark') {
      this.setDarkMode(true);
    } else {
      this.setDarkMode(false);
    }
  }

  setDarkMode(isDarkMode: boolean): void {
    if (isDarkMode) {
      document.documentElement.classList.add('dark');
      localStorage.setItem(this.themeKey, 'dark');
    } else {
      document.documentElement.classList.remove('dark');
      localStorage.setItem(this.themeKey, 'light');
    }
  }

  toggleTheme(): void {
    const currentTheme = localStorage.getItem(this.themeKey);
    this.setDarkMode(currentTheme !== 'dark');
  }

  isDarkMode(): boolean {
    return localStorage.getItem(this.themeKey) === 'dark';
  }
}
