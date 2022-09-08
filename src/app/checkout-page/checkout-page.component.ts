import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { RouteConfigLoadEnd } from '@angular/router';
import { BehaviorSubject, map, Observable, ReplaySubject, Subject } from 'rxjs';
import { CartItem } from '../Models/cart-item';
import { Category } from '../Models/categories';
import { Product } from '../Models/product';
import { Role } from '../Models/role';
import { User } from '../Models/user';
import { CartService } from '../services/cart-service/cart.service';
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
  cartItems: any;
  test: any;
  totalPrice: number = 0;
  totalQuantity: number = 0;
  total: number = 0;
  finalCart: Product[] = [];
  oldProducts$ = new BehaviorSubject<Product[]>([]);

  constructor(private orderService: OrdersService, private userService: UserService, private productService: ProductService, private cartService: CartService) { }

  ngOnInit(): void {
    this.listCartDetails();
    this.getProducts();
  }

  getProducts() {
    this.productService.getAllProducts()
      .subscribe({
        next: (data: Product[]) => {
          this.test = data;
          this.retrieveProducts(data);
          console.log(this.test);
        },
        error: (e) => console.error(e)
      });
  }

  retrieveProducts(products: Product[]) {
    this.oldProducts$.next(products);
    console.log("All = " + this.oldProducts$.getValue());
  }

  listCartDetails() {

    //get a handle to the cart items
    this.cartItems = this.cartService.cartItems;

    // subscribe to the cart totalPrice
    this.cartService.totalPrice.subscribe(
      data => this.totalPrice = data

    );

    // subscribe to the cart totalQuantity
    this.cartService.totalQuantity.subscribe(
      data => this.totalQuantity = data

    );
    // compute cart total price and quantity
    this.cartService.computeCartTotals();

  }

  incrementQuantity(theCartItem: CartItem) {
    this.cartService.addToCart(theCartItem);
  }
  decrementQuantity(theCartItem: CartItem) {
    this.cartService.decrementQuantity(theCartItem);
  }
  remove(theCartItem: CartItem) {
    this.cartService.remove(theCartItem);
  }

  addOrder(): void {
    let index = 0;
    let help: Product[] = [];
    let productIds = this.cartItems.map((item: Product) => item.productId);
    console.log(productIds);
    while (index < this.cartItems.length) {
      let valueHolder: Product[] = this.oldProducts$.getValue().map((item: Product) => item).filter((item: { productId: any; }) => item.productId == productIds[index]);
      help.push(valueHolder[0]);
      index++;
    }
    help.forEach(element => {
      const data = {
        productId: element.productId,
        name: element.name,
        description: element.description,
        price: element.price * this.cartItems.map((item: Product) => item).filter((item: { productId: any; }) => item.productId == element.productId).map(((item: { quantity: any; }) => item.quantity))[0],
        weight: element.weight,
        quantity: this.cartItems.map((item: Product) => item).filter((item: { productId: any; }) => item.productId == element.productId).map(((item: { quantity: any; }) => item.quantity))[0],
        image: element.image,
        categoryId: element.categoryId,
        rating: element.rating,
        category: element.category
      }
      this.finalCart.push(data);
    });

    console.log(this.finalCart);
    let dateString = new Date('2022-08-30T00:00:00');
    let role: Role[] = [{ roleId: 7, role: "ROLE_ADMIN" }]

    const data = {
      orderId: 6,
      amount: this.total,
      orderDate: dateString,
      status: true,
      billingAddress: this.inputAddress + " " + this.inputAptNo + ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode,
      shippingAddress: this.inputAddress + " " + this.inputAptNo + ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode,
      user: {
        userId: 3,
        firstName: "Blaise",
        lastName: "Harris",
        email: "123@123.com",
        userName: "blaise.harris",
        contact: "111",
        password: "$2a$10$8o97PrN4NBV9sc.ZgypcgeTBZQzxN4wGlI8siJd/ogoZIllg4sDxy",
        ssn: "1111",
        roles: role
      },
      products: this.finalCart
    };
    this.orderService.addAnOrder(data)
      .subscribe({
        next: (m: any) => {
          console.log(m);
          this.ngOnInit()
        },
        error: (e: any) => console.error(e)
      });
  }

  addressChanged(): string {
    if (this.inputAptNo != "") {
      return this.inputAddress + this.inputAptNo + ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode
    } else {
      return this.inputAddress + ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode
    }
  }

  changedName(event: any) {
    this.inputName = event.target.value;
  }

  changedAddress(event: any) {
    this.inputAddress = event.target.value;
  }

  changedCity(event: any) {
    this.inputCity = event.target.value;
  }

  changedAptNo(event: any) {
    this.inputAptNo = event.target.value;
  }

  changedState(event: any) {
    this.inputState = event.target.value;
  }

  changedCountry(event: any) {
    this.inputCountry = event.target.value;
  }

  changedZipcode(event: any) {
    this.inputZipcode = event.target.value;
  }

  changedOwner(event: any) {
    this.inputOwner = event.target.value;
  }

  changedCardNum(event: any) {
    this.inputCardNum = event.target.value;
  }

  changedExpDate(event: any) {
    this.inputExpDate = event.target.value;
  }

  changedCVV(event: any) {
    this.inputCVV = event.target.value;
  }
}