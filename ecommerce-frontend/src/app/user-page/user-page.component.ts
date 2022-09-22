import { Component } from '@angular/core';
import { CheckboxControlValueAccessor } from '@angular/forms';
import { Router } from '@angular/router';
import { ngbCarouselTransitionOut } from '@ng-bootstrap/ng-bootstrap/carousel/carousel-transition';
import { OktaAuthService } from '@okta/okta-angular';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.scss']
})
export class UserPageComponent {
  username: any;
  url = this.router.url
  isUser?: boolean;
  user?: User ;
  sub: string = "";
  constructor(private router: Router, private oktaAuthService: OktaAuthService, private userService: UserService) {
    this.oktaAuthService.getUser().then((user) => {
      this.sub = user.sub;
      this.validateUser(this.sub);
    });
  }


    validateUser(sub: string) {
      this.userService.getUserByOktaId(sub).subscribe((user) => {
        this.user = user;
        this.isUser = this.user?.roles[0].role == "ROLE_USER" ? true : false;
      });
    }
}

