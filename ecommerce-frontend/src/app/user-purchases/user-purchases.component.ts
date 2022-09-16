import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Orders } from '../Models/orders';
import { OrdersService } from '../services/orders-service/orders.service';

@Component({
    selector: 'app-user-purchases',
    templateUrl: './user-purchases.component.html',
    styleUrls: ['./user-purchases.component.scss']
  })

  export class UserPurchasesComponent implements OnInit {
  orders$ = new BehaviorSubject<Orders[]>([]);
  orders: Orders[] = [];
  cast = this.orders$.asObservable();
  constructor(private ordersService: OrdersService) { }
  display="none";

  ngOnInit(): void {
    this.getOrderSearch(1);
    this.cast.subscribe((orders) => {
      this.orders = orders;
    });
  }

  retrieveOrders(orders: Orders[]) {
    this.orders$.next(orders);
    
  }

  // get current authenticated user and only return the 
  // orders that belong to them

  getOrderSearch(id:number) {
    this.ordersService.getAllOrders()
      .subscribe ((data: any) => {
        this.cast = data;
        this.retrieveOrders(data);
        console.log("USER ORDERS: ", this.cast);
      })
  } 

  openModal(){
    this.display="block";
  }
  onCloseHandled(){
    this.display="none";
  }
    
  }