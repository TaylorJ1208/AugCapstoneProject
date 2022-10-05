import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OktaAuthService } from '@okta/okta-angular';
import { CartItem } from '../Models/cart-item';
import { Orders } from '../Models/orders';
import { Product } from '../Models/product';
import { CartService } from '../services/cart-service/cart.service';
import { OrdersService } from '../services/orders-service/orders.service';
import { ReviewService } from '../services/review-service/review.service';
import { Review } from '../Models/Review';
@Component({
    selector: 'app-user-purchases',
    templateUrl: './user-purchases.component.html',
    styleUrls: ['./user-purchases.component.scss']
  })
  export class UserPurchasesComponent implements OnInit {
    orders: Orders[] = [];
    searchWord: any ="";
    todayDate = new Date().toDateString();
    allReviews: Review[] = [];
    showAddReview: boolean=false;
    review: Review = {
      reviewId: 0,
      name: "",
      review: "",
      product: {
        productId: 0,
        name: "",
        description: "",
        price: 0,
        weight: 0,
        quantity: 0,
        image: "",
        rating: 0,
        category: {
          categoryId: 0,
          category: ""
        },
        vendors: {
          vendorId: 0,
          email: "",
          name: ""
        },
        reviews: [],
      },
      title: "",
      rating: 0
    }
  constructor(private ordersService: OrdersService, private oktaAuthService: OktaAuthService, 
    private modalService: NgbModal, private cartService: CartService, private reviewService: ReviewService) { }
  ngOnInit(): void {
    this.getReviews();
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
  open(content:any){
    this.modalService.open(content, {size: 'lg'});
  }
  addToCart(theProduct: Product) {
    const theCartItem = new CartItem(theProduct);
    this.cartService.addToCart(theCartItem);
  }
  viewProductReviews(items: any) {
    this.modalService.open(items, { size: 'lg', scrollable: true });
  }
  getReviews() {
    this.reviewService.getAllReviews().subscribe((data) => {
      this.allReviews = data;
      console.log(this.allReviews);
    })
  }
  saveReview(product: Product) {
    let newReview: Review = {
      reviewId: this.review.reviewId,
      name: this.review.name,
      review: this.review.review,
      product: {
        productId: product.productId,
        name: product.name,
        description: product.description,
        price: product.price,
        weight: product.weight,
        quantity: product.quantity,
        image: product.image,
        rating: product.rating,
        category: {
          categoryId: product.category.categoryId,
          category: product.category.category
        },
        vendors: {
          vendorId: product.vendors.vendorId,
          email: product.vendors.email,
          name: product.vendors.name
        },
        reviews: product.reviews,
      },
      title: this.review.title,
      rating: this.review.rating
    }
    console.log(newReview)
    this.reviewService.addReview(newReview).subscribe(() => {
      this.getReviews();
    });
    this.showAddReview = false;
  }
  }
