import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { Product } from 'src/app/Models/product';
import { ProductService } from '../../product-service/product.service';

@Injectable({
  providedIn: 'root'
})
export class NavService {
  private products = new BehaviorSubject<Product[]>([]);
  cast = this.products.asObservable();
  searchString: string = "";
  constructor(private productService: ProductService) { }

  retrieveSearch(products: Product[]) {
    return this.products.next(products);
  }

  getProductSearch(name: any) {
    this.productService.getProductByName(name)
      .subscribe ((data: any) => {
        this.cast = data;
        this.retrieveSearch(data);
        console.log(this.cast);
      })
  }

}
