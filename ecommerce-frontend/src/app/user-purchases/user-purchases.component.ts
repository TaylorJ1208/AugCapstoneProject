import { Component, OnInit } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { BehaviorSubject } from 'rxjs';
import { Orders } from '../Models/orders';
import { OrdersService } from '../services/orders-service/orders.service';

@Component({
    selector: 'app-user-purchases',
    templateUrl: './user-purchases.component.html',
    styleUrls: ['./user-purchases.component.scss']
  })

  export class UserPurchasesComponent implements OnInit {
  orders: Orders[] = [];
  constructor(private ordersService: OrdersService, private oktaAuthService: OktaAuthService) { }
  display="none";

  ngOnInit(): void {
    this.oktaAuthService.getUser().then((user) => {
      this.getOrderSearch(user.sub);
    })
    
  }

  getOrderSearch(oktaId:string) {
    this.ordersService.getAllOrders()
      .subscribe ((data: Orders[]) => {
        console.log(data.filter((order) => order.user.oktaId == oktaId));
        this.orders = data.filter((order) => order.user.oktaId == oktaId)
        console.log(this.orders);
      })
  } 

  openModal(){
    this.display="block";
  }
  onCloseHandled(){
    this.display="none";
  }
    
  }