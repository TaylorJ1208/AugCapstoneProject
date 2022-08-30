import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-user-admin-edit',
  templateUrl: './user-admin-edit.component.html',
  styleUrls: ['./user-admin-edit.component.scss']
})

export class UserAdminEditComponent implements OnInit {
  user: User = {
     userId: 0,
     firstName: '',
     lastName: '',
     email: '',
     userName: '',
     password: '',
     ssn: "0",
     contact: "0",
     roles: []
   };

  confirmPw:string = '';
  constructor(private route: ActivatedRoute, private userService: UserService, private router: Router) { 
      
  }
  
  ngOnInit(): void {
    const userId = this.route.snapshot.params["userId"];
    this.userService.getUserById(userId).subscribe(x => this.user = x);
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
        this.router.navigate(["user/admin/details"]);
        return true;
      }
      alert("Passwords do not match");
    }
    return false;
  }
  confirmMatchingPasswords(): boolean {
    
    this.confirmPw = (<HTMLInputElement>document.getElementById("confirmPassword")).value;
    //console.log(this.confirmPw)
   
    if(this.user.password.length != 0 && this.confirmPw.length != 0) {
      if(this.user.password === this.confirmPw) {
        console.log(this.user.password === this.confirmPw);
        return true;
      } 
  }

    return false;
  }
}
