import { Component, OnInit } from '@angular/core';
import { CartItem } from '../Models/cart-item';
import { Product } from '../Models/product';
import { CartService } from '../services/cart-service/cart.service';
import { ProductService } from '../services/product-service/product.service';

@Component({
	selector: 'app-cart-details',
	templateUrl: './cart-details.component.html',
	styleUrls: ['./cart-details.component.scss']
})
export class CartDetailsComponent implements OnInit {
	cartItems: CartItem[] = [];
	totalPrice: number = 0;
	totalQuantity: number = 0;
	currentProduct: any;
	constructor(private cartService: CartService, private productService: ProductService) { }

	ngOnInit(): void {
		this.listCartDetails();
		if (this.cartItems.length != 0) {
			for(let i = 0; i < this.cartItems.length; i++) {
				this.productService.getProductById(this.cartItems[i].productId)
				.subscribe((product: Product) => {
					this.currentProduct = product;
				});
			}
			
		}
	}

	listCartDetails() {

		//get a handle to the cart items
		this.cartItems = this.cartService.cartItems;

		// subscribe to the cart totalPrice
		this.cartService.totalPrice.subscribe(
			(data: any) => this.totalPrice = data

		);

		// subscribe to the cart totalQuantity
		this.cartService.totalQuantity.subscribe(
			data => this.totalQuantity = data

		);
		// compute cart total price and quantity
		this.cartService.computeCartTotals();

	}

	incrementQuantity(theCartItem: CartItem) {

		this.productService.getProductById(theCartItem.productId)
			.subscribe((product: Product) => {
				this.currentProduct = product;
			});
		this.cartService.addToCart(theCartItem);
	}
	decrementQuantity(theCartItem: CartItem) {
		this.cartService.decrementQuantity(theCartItem);
	}
	remove(theCartItem: CartItem) {
		this.cartService.remove(theCartItem);
	}
}
