import { Component, OnInit } from '@angular/core';
import { Product } from '../Models/product';
import { NavService } from '../services/nav-service/nav-service/nav.service';

@Component({
  selector: 'app-result-page',
  templateUrl: './result-page.component.html',
  styleUrls: ['./result-page.component.scss']
})
export class ResultPageComponent implements OnInit {
  products$ = this.navService.products$;
  cast = this.products$.asObservable();
  products: Product[] = [];
  constructor(private navService: NavService) {

  }

  ngOnInit(): void {
    this.cast.subscribe((products) => {
      this.products = products;
    });
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

}