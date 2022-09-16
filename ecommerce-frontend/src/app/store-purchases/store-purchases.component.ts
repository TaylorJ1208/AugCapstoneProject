import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Orders } from '../Models/orders';
import { OrdersService } from '../services/orders-service/orders.service';
import { UserService } from '../services/user-service/user.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Product } from '../Models/product';

@Component({
  selector: 'app-store-purchases',
  templateUrl: './store-purchases.component.html',
  styleUrls: ['./store-purchases.component.scss']
})
export class StorePurchasesComponent implements OnInit {
  orders$ = new BehaviorSubject<Orders[]>([]);
  orders: Orders[] = [];
  statuses: any = [true, false];
  cast = this.orders$.asObservable();
  constructor(private ordersService: OrdersService, private userService: UserService,
    private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getAllOrders();
    this.cast.subscribe((orders) => {
      this.orders = orders;
    });
  }

  retrieveOrders(orders: Orders[]) {
    this.orders$.next(orders);
  }

  getAllOrders() {
    this.ordersService.getAllOrders()
      .subscribe((data: any) => {
        this.cast = data;
        console.log("STORE ORDERS: ", data);
        this.retrieveOrders(data);

      })
  }

  onEdit(item: any, field: string) {
    item.editFieldName = field;
  }
  // Updates data when user exits inline-editing
  close(order: Orders) {
    order.editFieldName = '';
    this.updateOrder(order);
  }

  updateOrder(order: Orders) {
    this.ordersService.updateOrder(order)
      .subscribe((data: any) => {
        console.log("UPDATED ORDER", order);
        this.retrieveOrders(data);
        this.getAllOrders();
      })
  }

  verifyDeleteOrder(verifyDelete: any) {
    this.modalService.open(verifyDelete, {backdropClass: 'light-blue-backdrop'});
  }

  deleteOrder(order: Orders) {
    this.ordersService.deleteOrder(order.orderId)
    .subscribe((data: any) => {
      this.retrieveOrders(data);
      this.getAllOrders();
    })
  }

  viewOrderItems(items: any) {
    this.modalService.open(items, { size: 'xl' });
  }

  orderItems(order: Orders): Product[] {
    return order.products;
  }
  
// Should user be able to remove items from order???
  // updateOrderItems(order: Orders, product: Product): void {
  //   let items = order.products.filter((data) => data.productId != product.productId);
  //   let total = items.reduce((total, data) => total + data.price, 0);
  //   order.products = items;
  //   order.amount = total;
  //   this.updateOrder(order);

  // }

}
