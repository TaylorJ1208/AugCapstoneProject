import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Orders } from '../Models/orders';
import { OrdersService } from '../services/orders-service/orders.service';
import { User } from '../Models/user';
import { UserService } from '../services/user-service/user.service';

@Component({
  selector: 'app-store-purchases',
  templateUrl: './store-purchases.component.html',
  styleUrls: ['./store-purchases.component.scss']
})
export class StorePurchasesComponent implements OnInit {
  orders$ = new BehaviorSubject<Orders[]>([]);
  orders: Orders[] = [];
  cast = this.orders$.asObservable();
  constructor(private ordersService: OrdersService, private userService: UserService) { }

  ngOnInit(): void {
    this.getAllOrders();
    this.cast.subscribe((orders) => {
      this.orders = orders;
    });
  }

  retrieveOrders(orders: Orders[]) {
    this.orders$.next(orders);
  }

  updateUser(user: User): void {
    this.userService.updateUser(user)
      .subscribe((data: any) => {
        this.retrieveOrders(data);
      })
  }

  getAllOrders() {
    this.ordersService.getAllOrders()
      .subscribe((data: any) => {
        this.cast = data;
        this.retrieveOrders(data);

      })
  }

  onEdit(item: any, field: string) {
    item.editFieldName = field;
  }

  close(order: Orders) {
    const newOrder = {
      orderId: order.orderId,
      amount: order.amount,
      orderDate: order.orderDate,
      status: order.status,
      billingAddress: order.billingAddress,
      shippingAddress: order.shippingAddress,
      user: order.user,
      products: order.products
    }
    this.updateUser(order.user);
    order.editFieldName = '';
    this.updateOrder(newOrder);
    
  }

  updateOrder(order: Orders) {
    this.ordersService.updateOrder(order)
      .subscribe((data: any) => {
        this.retrieveOrders(data);
        this.getAllOrders();
      })
  }

}
