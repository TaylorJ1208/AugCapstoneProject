import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'src/app/Models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>("http://localhost:8081/catalog/customer");
  }

  getProductByName(name: string): Observable<Product[]> {
    return this.http.get<Product[]>(`http://localhost:8081/catalog/customer/product/${name}`);
  }

  removeProduct(productId: number){
    return this.http.delete(`http://localhost:8081/catalog/admin/delete/${productId}`);
  }
}
