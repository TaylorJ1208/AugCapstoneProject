import { Component, ElementRef, OnInit, ViewChild, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../Models/product';
import { ProductService } from '../services/product-service/product.service';

@Component({
  selector: 'app-update-catalog',
  templateUrl: './update-catalog.component.html',
  styleUrls: ['./update-catalog.component.scss']
})
export class UpdateCatalogComponent implements OnInit {
  products: Product[] = [];
  imageUrl: string = "https://res.cloudinary.com/drukcz14j/image/upload/v1661201584/ecommerce/iPhone-13-PNG-Cutout_wydwdd.png";

  @Input() editMode = false;
  @Input() addMode = false;

  @Input() currentProduct:any;

  updated = false;
  added = false;
  

  constructor(
    private productService:ProductService,
    private route: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.getProducts();
  }

  
  getProducts() {
    this.productService.getAllProducts()
      .subscribe({ next: (data: Product[]) => {
        console.log(data);
        this.products = data;
      },
      error: (e) => console.error(e)});
    }

    deleteProduct(id:any):void{
      this.productService.deleteProduct(id).subscribe({next:m=>{
        console.log(m);
        console.log("product deleted");
        this.ngOnInit();
      },
      error:(e)=>console.error(e)
      })
      this.ngOnInit();
    }

    addProduct():void{
      const data = {
        productId: this.currentProduct.productId,
        name: this.currentProduct.name,
        description: this.currentProduct.description,
        price: this.currentProduct.price,
        weight: this.currentProduct.weight,
        quantity: this.currentProduct.quantity,
        image: this.currentProduct.image,
        rating: this.currentProduct.rating,
        category: {
          categoryId: this.currentProduct.category.categoryId
        }
      };
      this.productService.addProduct(data)
      .subscribe({next:m=>{
        console.log(m);
        this.added = true;
        console.log("Product.added: " +this.added);
        this.ngOnInit();
      },
    error:e=>console.error(e)
  });
    }

    getProduct(id: any): void {
      this.editMode = true;
      this.productService.getProductById(id)
        .subscribe({
          next: (data) => {
            this.currentProduct = data;
            console.log(data);
            console.log("category id is:"+this.currentProduct.category.categoryId);
            console.log("rating is "+this.currentProduct.rating);
          },
          error: (e) => console.error(e)
        });
    }

  updateProduct():void{
    const data = {
      productId: this.currentProduct.productId,
      name: this.currentProduct.name,
      description: this.currentProduct.description,
      price: this.currentProduct.price,
      weight: this.currentProduct.weight,
      quantity: this.currentProduct.quantity,
      image: this.currentProduct.image,
      rating: this.currentProduct.rating,
      category: {
        categoryId: this.currentProduct.category.categoryId
      }
    };
console.log(data);

    this.productService.updateProduct(data)
    .subscribe({next:m=>{
      console.log(m);
      this.updated = true;
      console.log(this.updated);
      this.ngOnInit();
    },
  error:e=>console.error(e)
});
}

newUpdate():void{
  this.updated = false;
}

newAdd():void{
  this.added = false;
  this.currentProduct = {
    productId: 0,
    name: "",
    description: "",
    price: 0,
    weight: 0,
    quantity: 0,
    image: "",
    rating:0,
    category:{
      categoryId: 0,
    }
  }
}

hideEditMode(){
  this.editMode=false;
}

showAddMode(){
  this.addMode = true;
  this.editMode = false;
  this.newAdd();
}

hideAddMode(){
  this.addMode = false;
}

@ViewChild('btnCloseAddedAlert') btnCloseAddedAlert!:ElementRef;
@ViewChild('btnCloseUpdatedAlert') btnCloseUpdatedAlert!:ElementRef;
closeAlert(i:any){
  
  setTimeout(()=>{
    if(i == "added"){
    this.btnCloseAddedAlert.nativeElement.click();
    console.log("btnCloseAdded Clicked");
    }
    else if (i == "updated"){
      this.btnCloseUpdatedAlert.nativeElement.click();
      console.log("btnClose updated clicked");
    }
  
  }, 2000);
  
}

}
