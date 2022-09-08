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
  isAuthenticated!:boolean;
  username!:string;
  constructor(private navService: NavService,
     private route: Router, private categoryService: CategoryService, private oktaAuthService: OktaAuthService) {

      this.oktaAuthService.$authenticationState.subscribe(
      isAuth => this.isAuthenticated = isAuth
    );
  }

  async ngOnInit() {
    this.isAuthenticated = await this.oktaAuthService.isAuthenticated();
    console.log("is user authenticated: "+this.isAuthenticated);
    if(this.isAuthenticated){
      const userClaims = await this.oktaAuthService.getUser();
      this.username = userClaims.name || "";
      console.log("loggedUserIs: "+this.username);
    }
    this.getCategories();
    this.searchProducts();
  }

   logout(){
     this.oktaAuthService.signOut();
   }

  searchProducts(): void {
    if (this.searchString) {
      localStorage.setItem("1", this.searchString)
    }
    this.navService.getProductSearch(localStorage.getItem("1"));
  }

  getCategories(): void {
    this.categoryService.getCategories()
      .subscribe((categories) => {
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
  
}
