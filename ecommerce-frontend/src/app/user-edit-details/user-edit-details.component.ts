import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthService } from '@okta/okta-angular';
import { User } from 'src/app/Models/user';
import { UserService } from 'src/app/services/user-service/user.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-user-edit-details',
  templateUrl: './user-edit-details.component.html',
  styleUrls: ['./user-edit-details.component.scss']
})
export class UserEditDetailsComponent implements OnInit {
  user: User = {
    //GET THE CURRENT USER DETAILS TO INIT USER
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

  confirmPw:string = '';
  showPw: boolean = false;
  showConfirm: boolean = false;
  visibleSSN: boolean = false;

  constructor(private userService: UserService, private router: Router, private oktaAuthService: OktaAuthService,
    private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.spinner.show();
    this.oktaAuthService.getUser().then((user) => {
      this.userService.getUserByOktaId(user.sub).subscribe((x) => {
        this.user = x
        this.spinner.hide();
      });
    })
   
  }

 
  updateUser() : boolean {
    const data: User = {
      oktaId: this.user.oktaId,
      userId: this.user.userId,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email,
      userName: this.user.userName,
      password: this.user.password,
      ssn: this.user.ssn,
      contact: this.user.contact,
      roles: this.user.roles
    };

  if(confirm("Are you sure you would like to update this account?")) {
    if(this.confirmMatchingPasswords()) {
      this.userService.updateUser(data)
        .subscribe({
          next: (res) => {
            console.log(data);
          },
          error: (e) => console.error(e)
        });
        alert("Account Updated!");
        this.router.navigate(["/user/accountDetails"]);
        return true;
      }
      alert("Passwords do not match");
    }
    return false;
  }

  confirmMatchingPasswords(): boolean {
    this.confirmPw = (<HTMLInputElement>document.getElementById("confirmPassword")).value;
      if(this.user.password.length != 0 && this.confirmPw.length != 0) {
        if(this.user.password === this.confirmPw) {
          console.log("Passwords match: ", this.user.password === this.confirmPw);
          return true;
        } 
      }
    return false;
  }
  
  showPassword() {
    this.showPw = !this.showPw;
  }

  showConfirmPassword() {
    this.showConfirm = !this.showConfirm;
  }

  showSSN() {
    this.visibleSSN = !this.visibleSSN;
  }
}