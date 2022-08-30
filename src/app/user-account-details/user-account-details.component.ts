import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';

@Component({
    selector: 'app-user-account-details',
    templateUrl: './user-account-details.component.html',
    styleUrls: ['./user-account-details.component.scss']
  })

export class UserAccountDetailsComponent implements OnInit {
  user: User = {
    userId: 0,
    firstName: '',
    lastName: '',
    email: '',
    userName: '',
    password: '',
    ssn: '',
    contact: ''
  };
  
  ngOnInit(): void {}
  constructor(private userService: UserService, private router: Router) { 
    this.userService.getUserById(18).subscribe(x => this.user = x);
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
  editUser(userId: number){
    console.log("edit user " + userId);
    this.router.navigateByUrl(`user/editDetails/${userId}`);
  }
}  
