import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Charges } from 'src/app/Models/charges';


@Injectable({
  providedIn: 'root'
})
export class StripeService {

  constructor(private http: HttpClient) { }

  makePayment(charge: Charges): Observable<any> {
    // `source` is obtained with Stripe.js; see https://stripe.com/docs/payments/accept-a-payment-charges#web-create-token
    return this.http.post("http://localhost:8081/stripe/charge", charge);
  }
}