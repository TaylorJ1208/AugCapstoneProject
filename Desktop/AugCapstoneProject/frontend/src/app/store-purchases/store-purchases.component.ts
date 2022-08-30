import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Orders } from '../Models/Orders';
import { OrdersService } from '../services/orders-service/orders.service';
@Component({
  selector: 'app-store-purchases',
  templateUrl: './store-purchases.component.html',
  styleUrls: ['./store-purchases.component.scss']
})
export class StorePurchasesComponent implements OnInit {
  orders$ = new BehaviorSubject<Orders[]>([]);
  orders: Orders[] = [];
  cast = this.orders$.asObservable();
  constructor(private ordersService: OrdersService) { }

  ngOnInit(): void {
    this.getProductSearch();
    this.cast.subscribe((orders) => {
      this.orders = orders;
    });
  }

  retrieveOrders(orders: Orders[]) {
    this.orders$.next(orders);
  }

  getProductSearch() {
    this.ordersService.getAllOrders()
      .subscribe ((data: any) => {
        this.cast = data;
        this.retrieveOrders(data);
        console.log(this.cast);
      })
  }

}
