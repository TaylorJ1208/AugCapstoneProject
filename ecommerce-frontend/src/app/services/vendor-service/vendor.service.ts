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
    return this.http.get<any>("http://localhost:8081/vendors");
  }

  getVendorById(id: string): Observable<any> {
    return this.http.get<any>(`http://localhost:8081/vendors/${id}`);
  }


  deleteVendorById(id: any): Observable<any> {
    return this.http.delete(`http://localhost:8081/vendors/delete/${id}`);
  }

  updateVendor(data:any):Observable<any>{
    return this.http.put(`http://localhost:8081/vendors/update`,data);
  }

  addVendor(data:any):Observable<any>{
    return this.http.post(`http://localhost:8081/vendors/add`,data);
  }

 /*  requestStock(productId:any, amount:any, data:any):Observable<any>{
    return this.http.post(`http://localhost:8081/vendors/restock/${productId}/${amount}`, data);
  } */

  requestStock(venderemail:any, amount:any, data:any):Observable<any>{
    return this.http.post(`http://localhost:8081/vendors/restock/${venderemail}/${amount}`, data);
  }
}