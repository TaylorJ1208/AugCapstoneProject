import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class OktaService {

  constructor(private http: HttpClient) { }

  getAllOrders(): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/api/userProfile`);
  }
}
