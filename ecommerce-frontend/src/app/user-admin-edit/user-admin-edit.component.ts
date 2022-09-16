import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';
import { AddressService } from '../services/address-service/address.service';

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
     ssn: '',
     contact: '',
     roles: []
   };

  /* address: Address = {
    addressId: 0,
    city: '',
    state: '',
    street: '',
    zipcode: '',
    country: '',
    apartmentNumber: '',
    userId: 0
  } */



  confirmPw:string = '';
  constructor(private route: ActivatedRoute, private userService: UserService, private router: Router,
    private addressService: AddressService) { 
      
  }
  
  ngOnInit(): void {
    const userId = this.route.snapshot.params["userId"];
    this.userService.getUserById(userId).subscribe(x => this.user = x);
  }

  updateUser() : void {
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
      this.userService.updateUser(data)
        .subscribe({
          next: (res) => {
            console.log(data);
          },
          error: (e) => console.error(e)
        });
        alert("Account Updated!");
        this.router.navigate(["user/admin/details"]);
      }
    }
}