import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';
import { FormControl, FormGroup } from '@angular/forms';
import { Address } from '../Models/address';
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

   currentAddress: Address = {
    addressId: 0,
    city: '',
    state: '',
    street: '',
    zipcode: '',
    country: '',
    apartmentNumber: '',
    user: this.user
  };

  addresses?: Address[];

  confirmPw:string = '';
  constructor(private route: ActivatedRoute, private userService: UserService, private router: Router,
    private addressService: AddressService) {     
  }
  
  ngOnInit(): void {
    const userId = this.route.snapshot.params["userId"];
    this.userService.getUserById(userId).subscribe(x => this.user = x);
    this.currentAddress = this.retrieveCorrectAddress();
  }


  retrieveCorrectAddress(): Address {
    this.addressService.getAllAddresses()
      .subscribe({
        next: (data) => {
          this.addresses = data;
          this.addresses.forEach((address) => {
            if(address.user.userId == this.route.snapshot.params["userId"]) {
              this.currentAddress = address;
            }
          })
          console.log(data);
        },
        error: (e) => console.error(e)
      });
      return this.currentAddress;
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
            console.log(res);
          },
          error: (e) => console.error(e)
        });
        alert("Account Updated!");
        this.router.navigate(["user/admin/details"]);
      }
    }
}