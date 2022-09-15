import { Component, Input, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { BehaviorSubject } from 'rxjs';
import { CartItem } from '../Models/cart-item';
import { Charges } from '../Models/charges';
import { Product } from '../Models/product';
import { Role } from '../Models/role';
import { User } from '../Models/user';
import { CartService } from '../services/cart-service/cart.service';
import { OrdersService } from '../services/orders-service/orders.service';
import { ProductService } from '../services/product-service/product.service';
import { StripeService } from '../services/stripe-service/stripe-service';
import { UserService } from '../services/user-service/user.service';
import { VendorService } from '../services/vendor-service/vendor.service';
import { VenderRequestService } from '../services/vendorRequest-service/vender-request.service';

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
  token:any;
  finalCart: Product[] = [];
  paymentHandler: any = null;
  oldProducts$ = new BehaviorSubject<Product[]>([]);
  token$ = new BehaviorSubject<any>([]);
  isAuthenticated!:boolean;
  username!:string;
  charge:Charges = {
    amount: 0,
    currency: 'USD',
    source: "",
    description: ''
  };

  constructor(private orderService: OrdersService, private userService: UserService, private productService: ProductService, 
    private cartService: CartService, private vendorService: VendorService,
    private oktaAuthService: OktaAuthService, private stripeService: StripeService ) {
    this.oktaAuthService.$authenticationState.subscribe(
      (isAuth: any) => this.isAuthenticated = isAuth
    );
  }
  

  @Input() amount: any;
  @Input() description: any;

  confirmation: any;
  loading = false;

  async ngOnInit() {
    this.isAuthenticated = await this.oktaAuthService.isAuthenticated();
    console.log("is user authenticated: "+ this.isAuthenticated);
    if(this.isAuthenticated){
      const userClaims = await this.oktaAuthService.getUser();
      this.username = userClaims.locale || "";
      console.log("loggedUserIs: "+ this.username);
    }
    this.getProducts();
    this.listCartDetails();
  }

  getProducts() {
    this.productService.getAllProducts()
      .subscribe({
        next: (data: Product[]) => {
          this.test = data;
          this.retrieveProducts(data);
          console.log(this.test);
        },
        error: (e: any) => console.error(e)
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
      (data:any) => this.totalPrice = data

    );

    // subscribe to the cart totalQuantity
    this.cartService.totalQuantity.subscribe(
      (data: any) => this.totalQuantity = data

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

  async makePayment(amount: number) {
    this.paymentHandler = (<any>window).StripeCheckout.configure({
      key: 'pk_test_51LflSODncq9KJa816vSKNjdic3V39Y1HZ0ZvX4TmkpoqUdMGFmb9xBklhywlxvwcFPqyiKn0xOlxou0NJkaORrnw00SACxUFmk',
      locale:'auto',
      token: function (stripeToken: any) {
        console.log(stripeToken);
        retrieveToken(stripeToken);
      }
  });

  const retrieveToken = (token: any) => {
    this.token$.next(token);
    paymentStripe();
    console.log("TOKEN = " + this.token$.getValue());
    this.addOrder();
  }

  const paymentStripe = () => {
    this.charge = {
      amount: this.totalPrice,
      currency: "USD",
      description: "Second try",
      source: this.token$.getValue().id
    }

    console.log("Charge " + this.charge)
    console.log(this.token$.getValue());
     this.stripeService.makePayment(this.charge).subscribe((data:any) => {
       console.log(data);
    });
  }
  
  this.paymentHandler.open({
    name: 'Payment Information',
    description: 'Please enter details below',
    amount: amount * 100,
    locale: 'auto',
  });
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
      category: element.category,
      vendorId: element.vendorId
    }
    this.finalCart.push(data);
  });

  console.log(this.finalCart);
  let dateString = new Date('2022-08-30T00:00:00');
  let role: Role[] = [{ roleId: 1, role: "ROLE_ADMIN" }]

  const data = {
    orderId: 4,
    amount: this.totalPrice,
    orderDate: dateString,
    status: true,
    billingAddress: this.inputAddress + " " + this.inputAptNo + ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode,
    shippingAddress: this.inputAddress + " " + this.inputAptNo + ", " + this.inputCity + ", " + this.inputState + " " + this.inputZipcode,
    user: {
      userId: 1,
      firstName: "Blaise",
      lastName: "Harris",
      email: "user@user.com",
      userName: "blaise.harris",
      contact: "111",
      password: "$2a$10$Sm1BDB.gAQNSRPKcon0LRuSSS1zClyFl1p3rbCTVH7gUPL9UXG58W",
      ssn: "1111",
      roles: role
    },
    products: this.finalCart
  };
  this.orderService.addAnOrder(data)
    .subscribe({
      next: (m: any) => {
        console.log(m);
        this.productStockDecrement();
        this.ngOnInit()
      },
      error: (e: any) => console.error(e)
    });
}

productStockDecrement(){
  this.cartItems.forEach((item: { productId: any; quantity: number; })=>{
    var product:any;
    this.productService.getProductById(item.productId)
    .subscribe({next:(data)=>{
      product = data;
      product.quantity = product.quantity - item.quantity;
      console.log(product);

      this.productService.updateProduct(product)
    .subscribe({next:(m)=>{
      console.log(m);

      this.sendLowStockMessage(product.productId);
    }});
    }});
    
  })

}

sendLowStockMessage(id: any) {
  var product:any=[];
  this.productService.getProductById(id)
  .subscribe({next:(data)=>{
    if(data.quantity < 5){
      console.log("product below got low in stock:");
      console.log(data);
      this.vendorService.sendRabbitMQMessage(data.productId)
      .subscribe({next:(m)=>{
        console.log("rabbit message sent.");
      }})
    }
  }});
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
}