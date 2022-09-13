import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from 'src/app/Models/product';
import { ProductService } from '../../product-service/product.service';

@Injectable({
  providedIn: 'root'
})
export class NavService {
  products$ = new BehaviorSubject<Product[]>([]);
  cast = this.products$.asObservable();
  searchString: string = "";
  constructor(private productService: ProductService) { }

  retrieveSearch(products: Product[]) {
    this.products$.next(products);
  }

  retrieveCategorySearch(products: Product[], category: string) {
    this.products$.next(products.filter((c: any) => c.category.category == category));
  }

  getProductSearch(name: any) {
    this.productService.getProductByName(name)
      .subscribe ((data: any) => {
        this.cast = data;
        this.retrieveSearch(data);
        console.log("PRODUCT SEARCH ->", this.cast);
      })
  }

  getProductByCategory(name: string) {
    this.productService.getAllProducts()
      .subscribe((data: any) => {
        this.cast = data;
        this.retrieveCategorySearch(data, name);
      })
  }

}
