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
}