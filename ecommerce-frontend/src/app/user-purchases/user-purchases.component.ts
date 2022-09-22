import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { Orders } from '../Models/orders';
import { OrdersService } from '../services/orders-service/orders.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
    selector: 'app-user-purchases',
    templateUrl: './user-purchases.component.html',
    styleUrls: ['./user-purchases.component.scss']
  })

  export class UserPurchasesComponent implements OnInit {
  orders: Orders[] = [];
  constructor(private ordersService: OrdersService, private oktaAuthService: OktaAuthService,
    private spinner: NgxSpinnerService) { }
  display="none";

  ngOnInit(): void {
    this.spinner.show();
    this.oktaAuthService.getUser().then((user) => {
      this.getOrderSearch(user.sub);
    })
    
  }

  getOrderSearch(oktaId:string) {
   
    this.ordersService.getAllOrders()
      .subscribe ((data: Orders[]) => {
        this.orders = data.filter((order) => order.user.oktaId == oktaId)
        console.log(this.orders);
        setTimeout(() => {
          this.spinner.hide();
        }, 3500);
        this.spinner.hide();
      })
  } 

  openModal(){
    this.display="block";
  }
  onCloseHandled(){
    this.display="none";
  }
    
  }