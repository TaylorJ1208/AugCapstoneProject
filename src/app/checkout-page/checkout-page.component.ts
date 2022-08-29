import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { RouteConfigLoadEnd } from '@angular/router';
import { Observable } from 'rxjs';
import { Product } from '../Models/product';
import { Role } from '../Models/role';
import { User } from '../Models/user';
import { OrdersService } from '../services/orders-service/orders.service';
import { ProductService } from '../services/product-service/product.service';
import { UserService } from '../services/user-service/user.service';

@Component({
  selector: 'app-checkout-page',
  templateUrl: './checkout-page.component.html',
  styleUrls: ['./checkout-page.component.scss']
})
export class CheckoutPageComponent implements OnInit {
  inputName: string = "";
  inputAddress: string = "";
  inputCity: string = "";
  inputAptNo: string = "";
  inputState: string = "";
  inputZipcode: string = "";
  inputCountry: string = "";
  inputCardNum: string = "";
  inputExpDate: string = "";
  inputCVV: string = "";
  inputOwner: string = "";
  currentAddress: string = "";
  products:Product[] = [];

  constructor(private orderService:OrdersService, private userService:UserService, private productService:ProductService) { }

  ngOnInit(): void { 
    this.getProduct();
  }

  getProduct() {
    this.productService.getAllProducts()
      .subscribe({ next: (data: Product[]) => {
        this.products = data;
      },
      error: (e) => console.error(e)});
  }
  

  addOrder() :void {
    const time = Date.parse('29 Aug 2022 00:12:00 GMT');
    let role:Role[] = [{ roleId: 1, role: "ROLE_ADMIN"}]
      
    const data = {
      orderId: 3,
      amount: 1000,
      orderDate: time,
      status: true,
      billingAddress: this.inputAddress + " " + this.inputAptNo + ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode,
      shippingAddress: this.inputAddress + " " + this.inputAptNo + ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode,
      user: {
        userId: 1,
        firstName: "Blaise",
        lastName: "Harris",
        email: "123@123.com",
        userName: "blaise.harris",
        contact: "111",
        password: "$2a$10$8o97PrN4NBV9sc.ZgypcgeTBZQzxN4wGlI8siJd/ogoZIllg4sDxy",
        ssn: "1111",
        roles: role
      },
      products: this.products
    };
    this.orderService.addAnOrder(data)
    .subscribe({next:m =>{
      console.log(m);
      this.ngOnInit()
    },
    error:e=>console.error(e)
  });
  }

  addressChanged(): string {
    if(this.inputAptNo != "") {
      return this.inputAddress + this.inputAptNo+ ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode
    } else {
      return this.inputAddress + ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode
    }
  }

  changedName(event : any){
    this.inputName = event.target.value;
  }
  
  changedAddress(event : any){
    console.log("Hi " + JSON.stringify(this.products))
    this.inputAddress = event.target.value;
  }

  changedCity(event : any){
    this.inputCity = event.target.value;
  }

  changedAptNo(event : any){
    this.inputAptNo = event.target.value;
  }

  changedState(event : any){
    this.inputState = event.target.value;
  }

  changedCountry(event : any){
    this.inputCountry = event.target.value;
  }

  changedZipcode(event : any){
    this.inputZipcode = event.target.value;
  }

  changedOwner(event : any){
    this.inputOwner = event.target.value;
  }

  changedCardNum(event : any){
    this.inputCardNum = event.target.value;
  }

  changedExpDate(event : any){
    this.inputExpDate = event.target.value;
  }

  changedCVV(event : any){
    this.inputCVV = event.target.value;
  }

}
