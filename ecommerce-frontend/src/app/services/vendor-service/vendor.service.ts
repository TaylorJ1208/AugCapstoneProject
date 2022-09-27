import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VendorService {

  constructor(private http: HttpClient) { }

  getAllVendors(): Observable<any> {
    return this.http.get<any>("http://localhost:8080/vendors");
  }

  getVendorById(id: string): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/vendors/${id}`);
  }


  deleteVendorById(id: any): Observable<any> {
    return this.http.delete(`http://localhost:8080/vendors/delete/${id}`);
  }

  updateVendor(data:any):Observable<any>{
    return this.http.put(`http://localhost:8080/vendors/update`,data);
  }

  addVendor(data:any):Observable<any>{
    return this.http.post(`http://localhost:8080/vendors/add`,data);
  }

  sendRabbitMQMessage(id:number):Observable<any>{
    console.log("sending rabbitMQ message for product id "+id);
    return this.http.get<any[]>(`http://localhost:8080/restock/remind/${id}`);
  }

  requestStock(venderemail:any, amount:any, data:any):Observable<any>{
    return this.http.post(`http://localhost:8080/vendors/restock/${venderemail}/${amount}`, data);
  }
}