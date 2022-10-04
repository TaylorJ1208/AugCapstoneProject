import { Component, OnInit } from '@angular/core';
import { Product } from '../Models/product';
import { NavService } from '../services/nav-service/nav-service/nav.service';
import { CartService } from '../services/cart-service/cart.service';
import { CartItem } from '../Models/cart-item';
import { ReviewService } from '../services/review-service/review.service';
import { Review } from '../Models/Review';

@Component({
  selector: 'app-result-page',
  templateUrl: './result-page.component.html',
  styleUrls: ['./result-page.component.scss']
})
export class ResultPageComponent implements OnInit {
  products$ = this.navService.products$;
  cast = this.products$.asObservable();
  products: Product[] = [];
  allReviews: Review[] = [];
  constructor(private navService: NavService,
    private cartService: CartService, private reviewService: ReviewService) {

  }

  ngOnInit(): void {
    this.getReviews();
    this.cast.subscribe((products) => {
      this.products = products;
    });
  }

  getReviewCount(id: number) {
    let count = this.allReviews.filter((review) => review.product.productId == id).length
    return count;
  }

  getReviews() {
    this.reviewService.getAllReviews().subscribe((data) => {
      this.allReviews = data;
      console.log(this.allReviews);
    })
  }


  filterPrice0(): void {
    this.cast.subscribe((products) => {
      this.products = products.filter((e) => e.price <= 50)
    });
  }

  filterPrice51(): void {
    this.cast.subscribe((products) => {
      this.products = products.filter((e) => e.price > 50 && e.price <= 100)
    });
  }

  filterPrice101(): void {
    this.cast.subscribe((products) => {
      this.products = products.filter((e) => e.price > 100 && e.price <= 300)
    });
  }

  filterPrice301(): void {
    this.cast.subscribe((products) => {
      this.products = products.filter((e) => e.price > 300 && e.price <= 500)
    });
  }

  filterPrice500(): void {
    this.cast.subscribe((products) => {
      this.products = products.filter((e) => e.price > 500)
    });
  }

  filterRating(e: Event) {
    this.cast.subscribe((products) => {
      this.products = products.filter((product) => product.rating.toString() == ((e.target as HTMLButtonElement).value));
    });

  }

  addToCart(theProduct: Product) {
    const theCartItem = new CartItem(theProduct);
    
    this.cartService.addToCart(theCartItem);
    }

}