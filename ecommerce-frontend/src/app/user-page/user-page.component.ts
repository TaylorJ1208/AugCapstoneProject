import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.scss']
})
export class UserPageComponent implements OnInit {
  username: any;
  url = this.router.url
  constructor(private oktaAuthService: OktaAuthService, private router: Router) { }

  ngOnInit(): void {
    this.url = this.router.url;
    // this.oktaAuthService.getUser();
  }

  // getUsername(): void {
  //   this.oktaAuthService.getUser().then(
  //   (res) => {
  //     this.username = res.name;
  //     // retrieve the user's email from authentication response
  //     const theEmail = res.email;
  //     // now store the email in browser storage
  //     // this.storage.setItem('userEmail', JSON.stringify(theEmail));
      
  //   });
  // }
  

}
