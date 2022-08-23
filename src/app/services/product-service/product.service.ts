import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<any> {
    return this.http.get("http://localhost:8081/catalog/customer");
  }

  getProductByName(name: string): Observable<any> {
    return this.http.get(`http://localhost:8080/`)
  }

}
