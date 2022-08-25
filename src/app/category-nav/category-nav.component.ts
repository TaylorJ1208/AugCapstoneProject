import { Component, OnInit } from '@angular/core';
import { NavService } from '../services/nav-service/nav-service/nav.service';
import { ProductService } from '../services/product-service/product.service';
import { Product } from '../Models/product';

@Component({
  selector: 'app-category-nav',
  templateUrl: './category-nav.component.html',
  styleUrls: ['./category-nav.component.scss']
})
export class CategoryNavComponent implements OnInit {
  products: Product[] = [];
  constructor(private navService: NavService, private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  filterCategory(e: Event): void {
    this.navService.getProductByCategory((e.target as HTMLButtonElement).value);
  }

  getProducts() {
    this.productService.getAllProducts()
      .subscribe({ next: (data: Product[]) => {
        console.log("ALL PRODUCTS ->", data);
        this.products = data;
      },
      error: (e) => console.error(e)});
    }

}
