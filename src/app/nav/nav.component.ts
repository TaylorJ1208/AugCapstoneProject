import { Component, OnInit } from '@angular/core';
import { NavService } from '../services/nav-service/nav-service/nav.service';
import { ProductService } from '../services/product-service/product.service';
import { Router } from '@angular/router';
import { CategoryService } from '../services/category-service/category.service';
import { Category } from '../Models/categories';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {
  categories: Category[] = [];
  searchString: any = "";
  constructor(private navService: NavService, private productService: ProductService,
     private route: Router, private categoryService: CategoryService) {

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
    this.categoryService.getAllCategories()
      .subscribe((categories) => {
        this.categories = categories;
        console.log("CATEGORY DATA ->", this.categories);
      });
  }

  routeToResult():void {
    this.route.navigate(['result']);
  }
  
}
