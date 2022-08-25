import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-checkout-page',
  templateUrl: './checkout-page.component.html',
  styleUrls: ['./checkout-page.component.scss']
})
export class CheckoutPageComponent implements OnInit {
  inputName: string = "";
  inputAddress: string = "";
  inputCity: string = "";
  inputAptNo: string = "";
  inputState: string = "";
  inputZipcode: string = "";
  inputCountry: string = "";
  inputCardNum: string = "";
  inputExpDate: string = "";
  inputCVV: string = "";
  inputOwner: string = "";

  constructor() { }
  
  ngOnInit(): void {
    
  }

  changedName(event : any){
    this.inputName = event.target.value;
  }
  
  changedAddress(event : any){
    this.inputAddress = event.target.value;
  }

  changedCity(event : any){
    this.inputCity = event.target.value;
  }

  changedAptNo(event : any){
    this.inputAptNo = event.target.value;
  }

  changedState(event : any){
    this.inputState = event.target.value;
  }

  changedCountry(event : any){
    this.inputCountry = event.target.value;
  }

  changedZipcode(event : any){
    this.inputZipcode = event.target.value;
  }

  changedOwner(event : any){
    this.inputOwner = event.target.value;
  }

  changedCardNum(event : any){
    this.inputCardNum = event.target.value;
  }

  changedExpDate(event : any){
    this.inputExpDate = event.target.value;
  }

  changedCVV(event : any){
    this.inputCVV = event.target.value;
  }

}
