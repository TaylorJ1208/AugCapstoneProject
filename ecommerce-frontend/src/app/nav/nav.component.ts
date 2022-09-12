import { Component, OnInit } from '@angular/core';
import { NavService } from '../services/nav-service/nav-service/nav.service';
import { Router } from '@angular/router';
import { CategoryService } from '../services/category-service/category.service';
import { Category } from '../Models/categories';
import { OktaAuthService } from '@okta/okta-angular';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {
  categories: Category[] = [];
  searchString: any = "";
  router = this.route.url;
  constructor(private navService: NavService,
     private route: Router, private categoryService: CategoryService, private oktaAuthService: OktaAuthService) {

  }

  ngOnInit(): void {
    this.getCategories();
    this.searchProducts();
  }

  searchProducts(): void {
    if (this.searchString) {
      localStorage.setItem("1", this.searchString)
    }
    this.navService.getProductSearch(localStorage.getItem("1"));
  }

  getCategories(): void {
    this.categoryService.getCategories()
      .subscribe((categories: any) => {
        this.categories = categories;
        console.log("CATEGORY DATA ->", this.categories);
      });
  }

  routeToResult():void {
    this.route.navigate(['result']);
  }

  signOut(): void {
    this.oktaAuthService.signOut();
  }

  sendHome() {
    this.route.navigate(['home']);
  }
  
}