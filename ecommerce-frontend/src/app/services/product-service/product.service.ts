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
    return this.http.get<Product[]>(
      'https://e-backend.azurewebsites.net/catalog/customer'
    );
  }

  getProductByName(name: string): Observable<Product[]> {
<<<<<<< HEAD
    return this.http.get<Product[]>(`https://e-backend.azurewebsites.net/catalog/customer/product/${name}`);
=======
    return this.http.get<Product[]>(
      `https://e-backend.azurewebsites.net/catalog/customer/product/${name}`
    );
>>>>>>> af233171346fe2563e370d3fcd6ec37a6255bc13
  }

  getProductById(id: any): Observable<any> {
<<<<<<< HEAD
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
=======
    return this.http.get<any>(
      `https://e-backend.azurewebsites.net/catalog/customer/${id}`
    );
  }

  deleteProduct(id: any): Observable<any> {
    return this.http.delete(
      `https://e-backend.azurewebsites.net/catalog/admin/delete/${id}`
    );
  }

  updateProduct(data: any): Observable<any> {
    return this.http.put(
      `https://e-backend.azurewebsites.net/catalog/admin/update`,
      data
    );
  }

  addProduct(data: any): Observable<any> {
    return this.http.post(
      `https://e-backend.azurewebsites.net/catalog/admin/add`,
      data
    );
>>>>>>> af233171346fe2563e370d3fcd6ec37a6255bc13
  }
}
