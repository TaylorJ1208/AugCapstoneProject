import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';
import { Address } from '../Models/address';
import { AddressService } from '../services/address-service/address.service';

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
  }
  addresses?: Address[];
  
  ngOnInit(): void {}
  constructor(private userService: UserService, private router: Router,
    private addressService: AddressService) { 
    this.userService.getUserById(26).subscribe(x => this.user = x);
    this.currentAddress = this.retrieveCorrectAddress();
  }

  retrieveCorrectAddress(): Address {
    this.addressService.getAllAddresses()
      .subscribe({
        next: (data) => {
          this.addresses = data;
          this.addresses.forEach((address) => {
            if(address.user.userId == 26) {
              this.currentAddress = address;
            }
          })
          console.log(data);
        },
        error: (e) => console.error(e)
      });
      return this.currentAddress;
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