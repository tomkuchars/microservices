import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component } from '@angular/core';
import { map, Observable, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent {

  destroyed = new Subject<void>();
  maxWidth599!: Observable<boolean>;

  constructor(breakpointObserver: BreakpointObserver) {
    this.maxWidth599 = breakpointObserver
      .observe(['(max-width: 599px)'])
      .pipe(takeUntil(this.destroyed))
      .pipe(map(result => result.matches));
  }

  ngOnDestroy() {
    this.destroyed.next();
    this.destroyed.complete();
  }

  items = {
    settings: {
      url: 'http://localhost:8080/assets/settings-mfe.js',
      name: 'Settings',
      icon: 'settings'
    },
    rates: {
      url: '',
      name: 'Exchange rates',
      icon: 'currency_exchange'
    },
  }

  selectedItem: string | undefined;

  goToGithub(): void {
    document.location.href = 'https://github.com/tomkuchars/microservices';
  }

}
