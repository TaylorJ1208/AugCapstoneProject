import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  url = this.router.url;
  constructor(private router: Router){}
  title = 'ecommerce-frontend';

  isLogin(){
    this.url = this.router.url;
    return this.router.url === '/login'
  }
// For Category Nav
  isValidRoute() {
   this.url = this.router.url;
    return this.router.url === '/home' || this.router.url === '/result';
  }
}
