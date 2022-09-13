import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthService } from '@okta/okta-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  url = this.router.url;
  isAuthenticated: boolean = false;
  constructor(public oktaAuth: OktaAuthService, public router: Router){
      // Subscribe to authentication state changes
      this.oktaAuth.$authenticationState.subscribe(
        (isAuthenticated: boolean)  => this.isAuthenticated = isAuthenticated
      );
  }
  title = 'ecommerce-frontend';

  async ngOnInit() {
    this.url = this.router.url;
    // Get the authentication state for immediate use
    this.isAuthenticated = await this.oktaAuth.isAuthenticated();
  }

  isLogin(){
    this.url = this.router.url;
    return this.router.url === '/login'
  }
// For Category Nav
  isValidRoute() {
   this.url = this.router.url;
    return this.router.url.startsWith('/home') || this.router.url === '/result';
  }
}
