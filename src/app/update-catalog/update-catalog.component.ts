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

  @Input() currentProduct:any = {
    productId: 0,
    name: "",
    description: "",
    price: 0,
    weight: 0,
    quantity: 0,
    image: "",
    category:{
      categoryId: 0,
    }
  };

  submitted = false;
  

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

    getProduct(id: any): void {
      this.editMode = true;
      this.productService.getProductById(id)
        .subscribe({
          next: (data) => {
            this.currentProduct = data;
            console.log(data);
            console.log("category id is:"+this.currentProduct.category.categoryId);
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
      category: {
        categoryId: this.currentProduct.category.categoryId
      }
    };
console.log(data);
    this.productService.updateProduct(data)
    .subscribe({next:m=>{
      console.log(m);
      this.submitted = true;
      console.log(this.submitted);
    },
  error:e=>console.error(e)
});
}

newUpdate():void{
  this.submitted = false;
}

hideEditMode(){
  this.editMode=false;
}

@ViewChild('btnCloseAlert') btnCloseAlert!:ElementRef;
closeAlert(){
  
  setTimeout(()=>{
    this.btnCloseAlert.nativeElement.click();
  console.log("btnClose Clicked");
  }, 2000);
  
}

}
