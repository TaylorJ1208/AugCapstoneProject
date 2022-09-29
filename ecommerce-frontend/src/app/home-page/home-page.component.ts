import { Component, OnInit } from '@angular/core';
import { Product } from '../Models/product';
import { ProductService } from '../services/product-service/product.service';
import { CartService } from '../services/cart-service/cart.service';
import { CartItem } from '../Models/cart-item';
import { OktaAuthService } from '@okta/okta-angular';
import { Router } from '@angular/router';
import { UserService } from '../services/user-service/user.service';
import { User } from '../Models/user';
import { NgxSpinnerService } from 'ngx-spinner';
import { ReviewService } from '../services/review-service/review.service';
import { Review } from '../Models/Review';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  products: Product[] = [];
  allReviews: Review[] = [];
  showAddReview: boolean = false;
  users: any = {};
  password: string = "";
  imageUrl: string = "https://res.cloudinary.com/drukcz14j/image/upload/v1661201584/ecommerce/iPhone-13-PNG-Cutout_wydwdd.png";
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
  constructor(private cartService: CartService, private productService: ProductService, private oktaAuthService: OktaAuthService,
    private router: Router, private userService: UserService, private modalService: NgbModal,
    private spinner: NgxSpinnerService, private reviewService: ReviewService) {
    if (this.oktaAuthService.isLoginRedirect()) {
      this.oktaAuthService.handleLoginRedirect().then(() => {
        this.router.navigate(['home']);
      })
    }
  }


  ngOnInit(): void {
    this.spinner.show();
    this.getReviews();
    this.getProducts();
    this.oktaAuthService.getUser().then((u) => {
      let fullName = u.name?.split(" ");
      console.log(u.sub);
      console.log(!this.userExists(u.sub));
      if (!this.userExists(u.sub)) {
        let user: any = {
          oktaId: u.sub,
          userId: 0,
          lastName: fullName?.pop(),
          firstName: fullName?.pop(),
          email: u.email,
          userName: u.email,
          password: this.password,
          contact: "919-339-3801",
          ssn: "1234",
          roles: []
        }
        this.addUser(user);
        this.getUsers();
      }
    })
  }

  getReviewCount(id: number) {
    let count = this.allReviews.filter((review) => review.product.productId == id).length
    return count;
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
  getUsers() {
    this.userService.getAllUsers().subscribe();
  }
  addUser(user: User) {
    this.userService.createUser(user).subscribe();
  }
  userExists(sub: string) {
    this.userService.getUserByOktaId(sub).subscribe((data: any) => {
      this.users = data;
      if (Object.keys(data).length > 0) {
        return true;
      }
      return false;
    })
    return false;
  }

  getProducts() {
    this.productService.getAllProducts()
      .subscribe({
        next: (data: Product[]) => {
          this.products = data;
          this.spinner.hide();
          console.log(this.products);
        },
        error: (e) => console.error(e)
        
      });
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

  addToCart(theProduct: Product) {
    const theCartItem = new CartItem(theProduct);

    this.cartService.addToCart(theCartItem);
  }

}