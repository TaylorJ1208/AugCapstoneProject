import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/Models/user';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-user-edit-details',
  templateUrl: './user-edit-details.component.html',
  styleUrls: ['./user-edit-details.component.scss']
})
export class UserEditDetailsComponent implements OnInit {
  user: User = {
    //GET THE CURRENT USER DETAILS TO INIT USER
    userId: 1,
    firstName: 'LOGGED IN USER FIRST',
    lastName: 'LOGGED IN USER LAST',
    email: 'LOGGEDINUSEREMAIL@gmail.com',
    userName: 'admin',
    password: 'Password',
    ssn: 1233,
    contact: 123456780
  };

  confirmPw:string = '';
  showPw: boolean = false;
  showConfirm: boolean = false;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

 
  updateUser() : boolean {
    const data = {
      userId: this.user.userId,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email,
      userName: this.user.userName,
      password: this.user.password,
      ssn: this.user.ssn,
      contact: this.user.contact,
    };

  if(confirm("Are you sure you would like to update this account?")) {
    if(this.confirmMatchingPasswords()) {
      this.userService.updateUser(data)
        .subscribe({
          next: (res) => {
            console.log(res);
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
          console.log(this.user.password === this.confirmPw);
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
}