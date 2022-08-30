import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user-service/user.service';

@Component({
    selector: 'app-user-account-details',
    templateUrl: './user-account-details.component.html',
    styleUrls: ['./user-account-details.component.scss']
  })

export class UserAccountDetailsComponent implements OnInit {
  ngOnInit(): void {}
  constructor(private userService: UserService, private router: Router) { 

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
}  
