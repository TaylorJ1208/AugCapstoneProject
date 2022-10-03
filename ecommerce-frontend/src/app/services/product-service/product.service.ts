import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from 'src/app/Models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>("http://localhost:8080/catalog/customer");
  }


  getProductByName(name: string): Observable<Product[]> {
    return this.http.get<Product[]>(`https://e-backend.azurewebsites.net/catalog/customer/product/${name}`);
  }

  getProductById(id: any): Observable<any> {
    return this.http.get<any>(`https://e-backend.azurewebsites.net/catalog/customer/${id}`);
  }

  deleteProduct(id: any): Observable<any> {
    return this.http.delete(`https://e-backend.azurewebsites.net/catalog/admin/delete/${id}`);
  }

  updateProduct(data:any):Observable<any>{
    return this.http.put(`https://e-backend.azurewebsites.net/catalog/admin/update`,data);
  }

  addProduct(data:any):Observable<any>{
    return this.http.post(`https://e-backend.azurewebsites.net/catalog/admin/add`,data);
  }
}
