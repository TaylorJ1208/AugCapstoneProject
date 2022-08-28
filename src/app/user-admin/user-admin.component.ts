import { Component, OnInit } from '@angular/core';
import { User } from '../Models/user';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-user-admin',
  templateUrl: './user-admin.component.html',
  styleUrls: ['./user-admin.component.scss']
})
export class UserAdminComponent implements OnInit {

  users?: User[];
  currentUser: User = {userId: 0, firstName: '', lastName: '', userName: '', password: '', email: '', contact: 0, ssn: 0};
  currentIndex = -1;
  
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.retrieveUsers();

  }

  retrieveUsers(): void {
    this.userService.getAllUsers()
      .subscribe({
        next: (data) => {
          this.users = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  setActiveUser(user: User, index: number): void {
    this.currentUser = user;
    this.currentIndex = index;
  }

  deleteUser(userId: number): void {
    if(confirm("Are you sure to delete this account?")) {
      this.userService.deleteUser(userId)
        .subscribe({
          next: (res) => {
            console.log(res)
        },
        error: (e) => console.log(e)
      });
      alert("Account Deleted!");
      this.retrieveUsers();
  }
}
  

  editUser(user: User): void {

  }
}
