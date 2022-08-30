import { Component, ElementRef, OnInit, ViewChild, Input, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../Models/product';
import { Category } from '../Models/category';
import { ProductService } from '../services/product-service/product.service';
import { CategoryService } from '../services/category-service/category.service';

@Component({
  selector: 'app-update-catalog',
  templateUrl: './update-catalog.component.html',
  styleUrls: ['./update-catalog.component.scss']
})
export class UpdateCatalogComponent implements OnInit {
  products: Product[] = [];
  categories: Category[] = [];

  showProductMode = false;
  editProductMode = false;
  addProductMode = false;
  showCategoryMode = false;
  editCategoryMode = false;
  editCategoryModeId = 0;
  addCategoryMode = false;

  currentProduct:any;
  currentCategory:any;

  productUpdated = false;
  productAdded = false;
  categoryUpdated = false;
  categoryAdded = false;
  deletePopupCategoryId = 0;
  deletePopupProductId = 0;

  constructor(
    private productService:ProductService,
    private categoryService:CategoryService,
    private route: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.getProducts();
    this.getCategories();
    this.showMode();
  }

  showMode():void{
    
      if (this.router.url == "/catalog/update-products"){
        this.showProductMode = true;
      }
      else if(this.router.url == "/catalog/update-categories"){
        this.showCategoryMode = true;
      }
  
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
        this.productAdded = true;
        console.log("Product.added: " +this.productAdded);
        this.ngOnInit();
      },
    error:e=>console.error(e)
  });
    }

    getProduct(id: any): void {
      this.editProductMode = true;
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
      this.productUpdated = true;
      console.log(this.productUpdated);
      this.ngOnInit();
    },
  error:e=>console.error(e)
});
}

newUpdate():void{
  this.productUpdated = false;
}

newAdd():void{
  this.productAdded = false;
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
  };

  this.categoryAdded = false;
  this.currentCategory = {
    categoryId:0,
    category:""
  };

}

hideEditProductMode(){
  this.editProductMode=false;
}

showAddProductMode(){
  this.addProductMode = true;
  this.editProductMode = false;
  this.newAdd();
}

hideAddProductMode(){
  this.addProductMode = false;
}

@ViewChild('btnCloseProductAddedAlert') btnCloseProductAddedAlert!:ElementRef;
@ViewChild('btnCloseProductUpdatedAlert') btnCloseProductUpdatedAlert!:ElementRef;
@ViewChild('btnCloseCategoryAddedAlert') btnCloseCategoryAddedAlert!:ElementRef;
closeAlert(i:any){
  
  setTimeout(()=>{
    if(i == "productAdded"){
    this.btnCloseProductAddedAlert.nativeElement.click();
    console.log("btnCloseAdded Clicked");
    }
    else if (i == "productUpdated"){
      this.btnCloseProductUpdatedAlert.nativeElement.click();
      console.log("btnClose updated clicked");
    }
    else if (i == "categoryAdded"){
      this.btnCloseCategoryAddedAlert.nativeElement.click();
      console.log("btnClose category added clicked");
    }
  
  }, 2000);
  
}


//  Edit/Add/Delete Category
getCategories():void{
  this.categoryService.getCategories()
  .subscribe({ next: (data: Category[]) => {
    console.log(data);
    this.categories = data;
  },
  error: (e) => console.error(e)});
}

getCategory(id: any): void {
  this.editCategoryModeId = id;
  this.categoryUpdated = false;
  this.categoryService.getCategoryById(id)
    .subscribe({
      next: (data) => {
        this.currentCategory = data;
        console.log(data);
        console.log("category id is:"+this.currentCategory.categoryId);
      },
      error: (e) => console.error(e)
    });
}

updateCategory():void{
  const data = {
    categoryId: this.currentCategory.categoryId,
    category: this.currentCategory.category
  };
console.log(data);

  this.categoryService.updateCategory(data)
  .subscribe({next:m=>{
    console.log(m);
    this.categoryUpdated = true;
    this.editCategoryModeId = 0;
    console.log(this.categoryUpdated);
    this.ngOnInit();
  },
error:e=>console.error(e)
});
}

showAddCategoryMode(){
  this.addCategoryMode = true;
  this.newAdd();
}

hideAddCategoryMode(){
  this.addCategoryMode = false;
}

addCategory():void{
  const data = {
    categoryId: this.currentCategory.categoryId,
    category: this.currentCategory.category
  };
console.log(data);

  this.categoryService.addCategory(data)
  .subscribe({next:m=>{
    console.log(m);
    this.categoryAdded = true;
    console.log(this.categoryAdded);
    this.ngOnInit();
  },
error:e=>console.error(e)
});
}

deleteCategory(id:any):void{
  this.categoryService.deleteCategory(id)
  .subscribe({next:m=>{
    console.log(m);
    console.log("category deleted");
    this.ngOnInit();
  },
  error:(e)=>console.error(e)
  })
  this.ngOnInit();
}

}
