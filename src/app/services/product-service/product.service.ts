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

  
  getProductById(id: any): Observable<any> {
    return this.http.get<any>(`http://localhost:8081/catalog/customer/${id}`);
  }

  deleteProduct(id: any): Observable<any> {
    return this.http.delete(`http://localhost:8081/catalog/admin/delete/${id}`);
  }

  updateProduct(data:any):Observable<any>{
    return this.http.put(`http://localhost:8081/catalog/admin/update`,data);
  }

  addProduct(data:any):Observable<any>{
    return this.http.post(`http://localhost:8081/catalog/admin/add`,data);
  }

}