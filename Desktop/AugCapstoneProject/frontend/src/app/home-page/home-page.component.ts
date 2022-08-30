import { Component, OnInit } from '@angular/core';
import { CartItem } from '../Models/cart-item';
import { Product } from '../Models/product';
import { CartService } from '../services/cart-service/cart.service';
import { ProductService } from '../services/product-service/product.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  products: Product[] = [];
  imageUrl: string = "https://res.cloudinary.com/drukcz14j/image/upload/v1661201584/ecommerce/iPhone-13-PNG-Cutout_wydwdd.png";
  constructor(private cartService: CartService, private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts() {
    this.productService.getAllProducts()
      .subscribe({ next: (data: Product[]) => {
        this.products = data;
      },
      error: (e) => console.error(e)});
    }
  
	addToCart(theProduct: Product) {
	const theCartItem = new CartItem(theProduct);
	
	this.cartService.addToCart(theCartItem);
	}

}


