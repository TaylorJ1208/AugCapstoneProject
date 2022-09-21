import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Vendor } from 'src/app/Models/vendor';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VendorService {

  constructor(private http: HttpClient) { }

  getAllVendors(): Observable<any> {
    return this.http.get<any>("https://e-frontend.azurewebsites.net/vendors");
  }

  getVendorById(id: string): Observable<any> {
    return this.http.get<any>(`https://e-frontend.azurewebsites.net/vendors/${id}`);
  }


  deleteVendorById(id: any): Observable<any> {
    return this.http.delete(`https://e-frontend.azurewebsites.net/vendors/delete/${id}`);
  }

  updateVendor(data:any):Observable<any>{
    return this.http.put(`https://e-frontend.azurewebsites.net/vendors/update`,data);
  }

  addVendor(data:any):Observable<any>{
    return this.http.post(`https://e-frontend.azurewebsites.net/vendors/add`,data);
  }

 /*  requestStock(productId:any, amount:any, data:any):Observable<any>{
    return this.http.post(`https://e-frontend.azurewebsites.net/vendors/restock/${productId}/${amount}`, data);
  } */

  sendRabbitMQMessage(id:number):Observable<any>{
    console.log("sending rabbitMQ message for product id "+id);
    return this.http.get<any[]>(`https://e-frontend.azurewebsites.net/restock/remind/${id}`);
  }

  requestStock(venderemail:any, amount:any, data:any):Observable<any>{
    return this.http.post(`https://e-frontend.azurewebsites.net/vendors/restock/${venderemail}/${amount}`, data);
  }
}