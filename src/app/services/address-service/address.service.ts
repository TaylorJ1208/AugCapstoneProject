import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Address } from 'src/app/Models/address';


@Injectable({
  providedIn: 'root'
})
export class AddressService {

constructor(private http: HttpClient) { }
  getAddressById(addressId: number): Observable<Address> {
    return this.http.get<Address>("http://localhost:8081/address/" + addressId);
  }

  getAllAddresses(): Observable<Address[]> {
    return this.http.get<Address[]>('http://localhost:8081/address')
  }

  updateAddress(data: any): Observable<any> {
    return this.http.put("http://localhost:8081/address/update", data);
  }

  addAddress(data: any): Observable<any> {
    return this.http.post("http://localhost:8081/address/add", data);
  }
}