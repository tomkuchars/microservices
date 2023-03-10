import { BreakpointObserver } from '@angular/cdk/layout';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { map, Observable, Subject, takeUntil } from 'rxjs';

type Health = {
  status: 'UP' | 'DOWN';
};
type Items = {
  [key: string]: any;
};

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.sass']
})
export class MenuComponent {

  //TODO move to settings file
  microservices = {
    settings: {
      url: 'http://localhost:8080',
      name: 'Settings',
      icon: 'settings'
    },
    rates: {
      url: 'http://localhost:8081',
      name: 'Exchange rates',
      icon: 'currency_exchange'
    },
  }

  destroyed = new Subject<void>();
  maxWidth599: Observable<boolean>;

  constructor(
    breakpointObserver: BreakpointObserver,
    private http: HttpClient
  ) {
    this.maxWidth599 = breakpointObserver
      .observe(['(max-width: 599px)'])
      .pipe(takeUntil(this.destroyed))
      .pipe(map(result => result.matches));
  }

  ngOnDestroy() {
    this.destroyed.next();
    this.destroyed.complete();
  }

  ngOnInit() {
    this.setItems();
  }

  items: Items = {};

  selectedItem: string | undefined;

  private setItems() {
    Object.entries(this.microservices).forEach(([key, value]) => {
      this.http.get<Health>(value.url + '/actuator/health')
        .subscribe(health => {
          if (health.status === 'UP') {
            this.items[key] = value;
            this.items[key].url += '/assets/' + key + '-mfe.js';
          }
        });
    });
  }

  goToGithub(): void {
    document.location.href = 'https://github.com/tomkuchars/microservices';
  }

}
