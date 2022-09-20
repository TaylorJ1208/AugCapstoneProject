import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthService } from '@okta/okta-angular';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';

@Component({
    selector: 'app-user-account-details',
    templateUrl: './user-account-details.component.html',
    styleUrls: ['./user-account-details.component.scss']
  })

export class UserAccountDetailsComponent {
  user: User = {
    oktaId: "",
    userId: 0,
    firstName: '',
    lastName: '',
    email: '',
    userName: '',
    password: '',
    ssn: '',
    contact: '',
    roles: []
  };

  constructor(private userService: UserService, private router: Router, private oktaAuthService: OktaAuthService) { 
    this.oktaAuthService.getUser().then((user) => {
      this.userService.getUserByOktaId(user.sub).subscribe(x => this.user = x);
    });
  }

  deleteUser(userId: number) : void {
    if(confirm("Are you sure to delete your account?")) {
      this.userService.deleteUser(userId)
        .subscribe({
          next: (res) => {
            console.log(res)
        },
        error: (e) => console.log(e)
      });
      alert("Account Deleted!");
      this.router.navigate(['home']);
    }
  }
  editUser(userId: number): void {
    console.log("edit user " + userId);
    this.router.navigateByUrl(`user/editDetails/${userId}`);
  }
}  