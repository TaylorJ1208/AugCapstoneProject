import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthService } from '@okta/okta-angular';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.scss']
})
export class UserPageComponent implements OnInit {
  username: any;
  url = this.router.url
  isUser?: boolean;
  user?: User ;
  sub: string = "";
  constructor(private router: Router, private oktaAuthService: OktaAuthService, private userService: UserService) {
    this.oktaAuthService.getUser().then((user) => {
      this.sub = user.sub;
    });
    this.userService.getUserByOktaId("00u6i0o1a9t0ZLk8r5d7").subscribe((user) => {
      this.user = user;
      this.isUser = this.user?.roles[0].role == "ROLE_USER" ? true : false;
    });
   }

  ngOnInit(): void {
  }
}

