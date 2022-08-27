import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Orders } from '../Models/Orders';
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

  ngOnInit(): void {
    this.getOrderSearch();
    this.cast.subscribe((orders) => {
      this.orders = orders;
    });
  }

  retrieveOrders(orders: Orders[]) {
    this.orders$.next(orders);
    
  }

  getOrderSearch() {
    this.ordersService.getAllOrders()
      .subscribe ((data: any) => {
        this.cast = data;
        this.retrieveOrders(data);
        console.log(this.cast);
      })
  } 

  
    
  }