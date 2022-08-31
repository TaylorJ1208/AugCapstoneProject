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

  ngOnInit():void {
    this.url = this.router.url;
  }

  isLogin(){
    this.url = this.router.url;
    return this.router.url === '/login'
  }
// For Category Nav
  isValidRoute() {
   this.url = this.router.url;
    return this.router.url === '/home' || this.router.url === '/result'
    || this.router.url === "/home?code=wcmHfhaJam4jr-qoYDcGDlT_KPU_l26y2brJzIjY5Zc&state=YBAP6AFWjMOQxX3bfHexZatQGsl312fA0C5oAxI0iJNexnaVOci6Zoe7pxop9BQ8";
  }
}
