import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/Models/user';
import { UserService } from 'src/app/services/user-service/user.service';
import { AddressService } from '../services/address-service/address.service';
import { Address } from '../Models/address';

@Component({
  selector: 'app-user-edit-details',
  templateUrl: './user-edit-details.component.html',
  styleUrls: ['./user-edit-details.component.scss']
})
export class UserEditDetailsComponent implements OnInit {
  user: User = {
    //GET THE CURRENT USER DETAILS TO INIT USER
    userId: 0,
    firstName: '',
    lastName: '',
    email: '',
    userName: '',
    password: '',
    ssn: '',
    contact: '',
    roles: []
  }
  addresses?: Address[];
  
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


  confirmPw:string = '';
  showPw: boolean = false;
  showConfirm: boolean = false;
  visibleSSN: boolean = false;

  constructor(private route: ActivatedRoute, private userService: UserService, private router: Router,
    private addressService: AddressService) { }

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

/*
  if(confirm("Are you sure you would like to update this account?")) {
      this.userService.updateUser(userData)
        .subscribe({
          next: (res) => {
            console.log(res);
          },
          error: (e) => console.error(e)
        });
        if(addressData.addressId != 0) {
          this.addressService.updateAddress(addressData).subscribe({
            next: (res) => {
              console.log(res);
            },
            error: (e) => console.error(e)
          });
        }
        else if(addressData.addressId == 0) {
          this.addressService.addAddress(addressData).subscribe({
            next: (res) => {
              console.log(res);
            },
            error: (e) => console.error(e)
          });
        }

        alert("Account Updated!");
        this.router.navigate(["user/admin/details"]);
      }
    } */
 
  updateUser() : boolean {
    const userData = {
      userId: this.user.userId,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email,
      userName: this.user.userName,
      password: this.user.password,
      ssn: this.user.ssn,
      contact: this.user.contact,
    };

    const addressData = {
      addressId: this.currentAddress.addressId,
      city: this.currentAddress.city,
      street: this.currentAddress.street,
      state: this.currentAddress.state,
      zipcode: this.currentAddress.zipcode,
      country: this.currentAddress.country,
      apartmentNumber: this.currentAddress.apartmentNumber,
      user: this.user
    };
    

  if(confirm("Are you sure you would like to update this account?")) {
    if(this.confirmMatchingPasswords()) {
      this.userService.updateUser(userData)
        .subscribe({
          next: (res) => {
            console.log(res);
          },
          error: (e) => console.error(e)
        });
        if(addressData.addressId != 0) {
          this.addressService.updateAddress(addressData).subscribe({
            next: (res) => {
              console.log(res);
            },
            error: (e) => console.error(e)
          });
        }
        else if(addressData.addressId == 0) {
          this.addressService.addAddress(addressData).subscribe({
            next: (res) => {
              console.log(res);
            },
            error: (e) => console.error(e)
          });
        }
        alert("Account Updated");
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