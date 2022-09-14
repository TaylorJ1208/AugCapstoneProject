import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Product } from '../Models/product';
import { Vendor } from '../Models/vendor';
import { ProductService } from '../services/product-service/product.service';
import { VendorService } from '../services/vendor-service/vendor.service';
import { VenderRequestService } from '../services/vendorRequest-service/vender-request.service';
import { Router } from '@angular/router';
import { VendorRequest } from '../Models/vendorRequest';

@Component({
  selector: 'app-inventory-page',
  templateUrl: './inventory-page.component.html',
  styleUrls: ['./inventory-page.component.scss']
})
export class InventoryPageComponent implements OnInit {

  constructor(
    private vendorService:VendorService, 
    private productService:ProductService,
    private vendorRequestService:VenderRequestService, 
    private router:Router) { }

  showVendorMode=false;
  addVendorMode = false;
  updateVendorMode = false;
  showTrackRequestsMode = false;

  requestStockMode=false;

  vendorAdded = false;
  vendorUpdated = false;
  editVendorModeId = 0;
  deletePopupVendorId = 0;

  requestPopupProductId = 0;
  requestDeletePopupId=0;
  requestQuantity=1;
  stockRequested=false;

  currentProduct:any;
  currentVendor:any;
  currentRequest:any;

  ngOnInit(): void {
    this.showMode();
    this.getVendors();
    this.getProducts();
    this.getVenderRequests();
  }


  products:any[]=[];
  vendors:Vendor[]=[];
  vendorRequests:VendorRequest[]=[];

  showMode():void{
    
    if (this.router.url == "/user/admin/update-vendors"){
      this.showVendorMode = true;
    }
    else if(this.router.url == "/user/admin/request-stock"){
      this.requestStockMode = true;
    }
    else if(this.router.url == "/user/admin/track-requests"){
      this.showTrackRequestsMode = true;
    }

}

  getProducts(){

      this.productService.getAllProducts()
        .subscribe({ next: (data: Product[]) => {
          console.log(data);
          this.products = data;
        },
        error: (e) => console.error(e)});
      
  }

  getVendors(){
    this.vendorService.getAllVendors()
    .subscribe({next: (data: Vendor[]) => {
      console.log(data);
      this.vendors = data;
    },
    error: (e) => console.error(e)
  });
  }

  getVendor(id:any){
    this.editVendorModeId = id;
    this.vendorService.getVendorById(id)
    .subscribe({next:(data: Vendor)=>{
      console.log(data);
      this.currentVendor = data;
    },
    error: (e) => console.error(e)
  });
  }

  addVendor(){
    const data={
      name:this.currentVendor.name,
      email:this.currentVendor.email
    }

    this.vendorService.addVendor(data)
    .subscribe({next:m=>{
      console.log("Vendor '"+data.name +"' Added");
      this.vendorAdded = true;
      this.ngOnInit();
    }})
  }

  updateVendor(){
    const data = {
      vendorId: this.currentVendor.vendorId,
        name:this.currentVendor.name,
        email:this.currentVendor.email
    }
    console.log("data is: ");
    console.log(data);

    this.vendorService.updateVendor(data)
    .subscribe({next:(m)=>{
      console.log(m);
      this.editVendorModeId = 0;
      this.ngOnInit();
    },
  error:e=>console.log(e)})
  }
  deleteVendor(id:any){
    this.vendorService.deleteVendorById(id)
    .subscribe({next:(m)=>{
      console.log("Vendor Deleted");
      this.ngOnInit();
    },
  error:e=>console.log(e)})
  }

  newAdd(){
    this.currentVendor={
      vendorId:0,
      name:"",
      email:""
    };
    this.vendorAdded=false;
  }

  @ViewChild('btnCloseVendorAddedAlert') btnCloseVendorAddedAlert!:ElementRef;
  @ViewChild('btnCloseStockRequestedAlert') btnCloseStockRequestedAlert!:ElementRef;
  closeAlert(i:string){
    if(i == "VendorAddedAlert"){
      if(this.vendorAdded){
      setTimeout(()=>{
        this.btnCloseVendorAddedAlert.nativeElement.click();
      },2000)
    }
    }

    if(i == "StockRequestedAlert"){
      if(this.stockRequested){
      setTimeout(()=>{
        this.btnCloseStockRequestedAlert.nativeElement.click();
      },2000)
    }
  }
    
  }

  requestStock(product:any){

    console.log("Requesting "+this.requestQuantity +" units of "+product.name + " from vendor '"+product.vendors.name+" '.");
    
    this.vendorService.requestStock(product.vendors.email, this.requestQuantity, product)
    .subscribe({next:(m)=>
    {
      console.log("Stock Requested.");
      this.stockRequested = true;
      this.requestPopupProductId = 0;
      this.ngOnInit();
    }});

    const reqData = {
      id:0,
      product:product,
    quantityrequested:this.requestQuantity,
    requestdate: new Date(Date.now()),
    vendor:product.vendors,
    status:"Sent"
    };

    this.vendorRequestService.addRequest(reqData)
    .subscribe({next:(m)=>{
      console.log(m);
    }});
    console.log(reqData);
    
  }

  updateRequestStatus(id:any, status:string){

    this.vendorRequestService.getRequestById(id)
    .subscribe({next:(data)=>{
     this.currentRequest = data;
     this.currentProduct = data.product;
     console.log(this.currentRequest);
     this.currentRequest.status = status;
     if(status == "Received"){
     this.currentProduct.quantity = this.currentProduct.quantity + data.quantityrequested;
     }
     else if (status == "Sent"){
      this.currentProduct.quantity = this.currentProduct.quantity - data.quantityrequested;
     }
     updateRequest();
    }});
   
    
  const updateRequest = ()=>{ this.vendorRequestService.updateStatus(this.currentRequest)
    .subscribe({next:(m)=>{
      console.log(m);
      updateStock();
    }});
    console.log(this.currentRequest);
  }

  const updateStock = ()=> {
    this.productService.updateProduct(this.currentProduct)
    .subscribe({next:()=>{
      console.log("stock updated");
      this.ngOnInit();
    }})
  }
  }

  getVenderRequests(){
    this.vendorRequestService.getAllRequests()
    .subscribe({next:(data)=>{
      console.log(data);
      this.vendorRequests = data;
    }})
  }

  deleteRequest(id:any){
    this.vendorRequestService.deleteRequestById(id)
    .subscribe({next:()=>{
      console.log("request deleted");
      this.ngOnInit();
    }})
  }

}
