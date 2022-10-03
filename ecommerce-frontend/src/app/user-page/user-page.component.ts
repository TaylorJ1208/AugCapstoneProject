import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthService } from '@okta/okta-angular';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.scss']
})
export class UserPageComponent {
  username: any;
  url = this.router.url
  isUser?: boolean;
  user?: User;
  userName?: string = "";
  sub: string = "";
  constructor(private router: Router, private oktaAuthService: OktaAuthService, private userService: UserService,
    private spinner: NgxSpinnerService) {
    this.spinner.show();
    this.oktaAuthService.getUser().then((user) => {
      this.sub = user.sub;
      this.userName = user.name;
      this.validateUser(this.sub);
    });

  }
  
  validateUser(sub: string) {
    this.userService.getUserByOktaId(sub).subscribe((user) => {
      this.user = user;
      this.isUser = this.user?.roles[0].role == "ROLE_USER" ? true : false;
      this.spinner.hide();
    });
  }
}

