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
  isUser: boolean = true;
  user?: User ;
  constructor(private router: Router, private oktaAuthService: OktaAuthService, private userService: UserService) { }

  ngOnInit(): void {
    this.url = this.router.url;
    this.oktaAuthService.getUser().then((user) => {
      this.checkIfUser(user.sub);
    });
  }

  checkIfUser(sub: any) {
    this.userService.getUserByOktaId(sub).subscribe((user) => {
      console.log(user);
      this.user = user;
    })
    if(this.user?.roles[0].role == "ROLE_USER") {
      this.isUser = true;
    }
  }

  

}

