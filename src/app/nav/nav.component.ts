import { Component, OnInit } from '@angular/core';
import { NavService } from '../services/nav-service/nav-service/nav.service';
import { ProductService } from '../services/product-service/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {
  searchString: any = "";
  constructor(private navService: NavService, private productService: ProductService, private route: Router) {

  }

  ngOnInit(): void {
    this.searchProducts();
  }

  searchProducts(): void {
    if (this.searchString) {
      localStorage.setItem("1", this.searchString)
      this.route.navigate(['result']);
    }
    this.navService.getProductSearch(localStorage.getItem("1"));
  }

}
