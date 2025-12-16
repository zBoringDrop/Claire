import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from "@angular/router";
import { NgClass } from '@angular/common';

interface MenuOption {
  title: string, 
  link: string, 
  img: string
}

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, RouterLinkActive, NgClass],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  options: MenuOption[] = [
    {title: 'Analysis', link: '/analysis', img: 'fi-sr-bolt'},
    {title: 'History', link: '/history', img: 'fi-sr-file-medical-alt'},
    {title: 'Sources', link: '/sources', img: 'fi-sr-folder'},
    {title: 'Stats', link: '/stats', img: 'fi-sr-chart-pie'},
    {title: 'Settings', link: '/settings', img: 'fi-sr-settings'}
  ]

  closed = signal(true)

  toggleMenu() {
    this.closed.update(v => !v)
  }

}
