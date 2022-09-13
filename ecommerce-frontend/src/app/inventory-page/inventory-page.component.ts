import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Product } from '../Models/product';
import { Vendor } from '../Models/vendor';
import { ProductService } from '../services/product-service/product.service';
import { VendorService } from '../services/vendor-service/vendor.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inventory-page',
  templateUrl: './inventory-page.component.html',
  styleUrls: ['./inventory-page.component.scss']
})
export class InventoryPageComponent implements OnInit {

  constructor(private vendorService:VendorService, private productService:ProductService, private router:Router) { }

  showVendorMode=false;
  addVendorMode = false;
  updateVendorMode = false;

  requestStockMode=false;

  vendorAdded = false;
  vendorUpdated = false;
  editVendorModeId = 0;
  deletePopupVendorId = 0;

  requestPopupProductId = 0;
  requestQuantity=1;
  stockRequested=false;

  currentProduct:any;
  currentVendor:any;

  ngOnInit(): void {
    this.showMode();
    this.getVendors();
    this.getProducts();
  }


  products:any[]=[];
  vendors:Vendor[]=[];

  showMode():void{
    
    if (this.router.url == "/user/admin/update-vendors"){
      this.showVendorMode = true;
    }
    else if(this.router.url == "/user/admin/request-stock"){
      this.requestStockMode = true;
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
      setTimeout(()=>{
        this.btnCloseVendorAddedAlert.nativeElement.click();
      },2000)
    }

    if(i == "StockRequestedAlert"){
      setTimeout(()=>{
        this.btnCloseStockRequestedAlert.nativeElement.click();
      },2000)
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
    }})
    
  }

}
