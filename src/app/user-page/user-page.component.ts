import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.scss']
})
export class UserPageComponent implements OnInit {
  username: any;
  constructor(private oktaAuthService: OktaAuthService) { }

  ngOnInit(): void {
    this.getUsername();
  }

  getUsername(): void {
    this.oktaAuthService.getUser().then(
    (res) => {
      this.username = res.name;
      // retrieve the user's email from authentication response
      const theEmail = res.email;
      // now store the email in browser storage
      // this.storage.setItem('userEmail', JSON.stringify(theEmail));
      
    });
  }
  

}
