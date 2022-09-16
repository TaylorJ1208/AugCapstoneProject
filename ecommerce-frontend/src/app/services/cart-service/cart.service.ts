import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { CartItem } from 'src/app/Models/cart-item';
import { AppInsightsService } from '../appInsights-service/app-insights.service';

@Injectable({
	providedIn: 'root'
})
export class CartService {


	cartItems: CartItem[] = [];

	totalPrice: Subject<number> = new Subject<number>();
	totalQuantity: Subject<number> = new Subject<number>();

	constructor(private appInsightsService: AppInsightsService) { }

	addToCart(theCartItem: CartItem) {
		this.appInsightsService.logEvent("Add To Cart Attempt");
		// check if we already have the item in our cart


		let alreadyExistsInCart: boolean = false;
		let existingCartItem: CartItem = undefined!;

		if (this.cartItems.length > 0) {
			//find the item in the cart based on item id

			for (let tempCartItem of this.cartItems) {
				if (tempCartItem.productId == theCartItem.productId) {
					existingCartItem = tempCartItem;
					break;
				}
			}

			// check if we found it
			alreadyExistsInCart = (existingCartItem != undefined);
		}


		if (alreadyExistsInCart) {
			existingCartItem.quantity++;
		}
		else {
			// just add the item to the array
			this.cartItems.push(theCartItem);
		}
		//compute the cart total price and total quantity
		this.computeCartTotals();
	}
	computeCartTotals() {
		let totalPriceValue: number = 0;
		let totalQuantityValue: number = 0;
		for (let currentCartItem of this.cartItems) {
			totalPriceValue += currentCartItem.quantity * currentCartItem.price;
			totalQuantityValue += currentCartItem.quantity;
		}

		//publish the new values .. all subscribers will receive the new data

		this.totalPrice.next(totalPriceValue);
		this.totalQuantity.next(totalQuantityValue);

		//log cart data just for debugging purposes
		this.logCartData(totalPriceValue, totalQuantityValue)

	}
	logCartData(totalPriceValue: number, totalQuantityValue: number) {
		console.log('Contents of the cart');
		for (let tempCartItem of this.cartItems) {
			const subTotalPrice = tempCartItem.quantity * tempCartItem.price;
			console.log(`name: ${tempCartItem.name}, quantity: ${tempCartItem.quantity}, price: ${tempCartItem.price}, subTotalPrice=${subTotalPrice}`);
		}

		console.log(`totalPrice: ${totalPriceValue.toFixed(2)}, totalQuantity: ${totalQuantityValue}`);
		console.log('-------');
	}
	decrementQuantity(theCartItem: CartItem) {
		if (theCartItem.quantity > 1) {
			theCartItem.quantity--;
		}
		this.computeCartTotals();

	}
	remove(theCartItem: CartItem) {
		//get index of item in the array
		const itemIndex = this.cartItems.findIndex(tempCartItem => tempCartItem.productId === theCartItem.productId);

		//if found, remove the item from the array at the given index
		if (itemIndex > -1) {
			this.cartItems.splice(itemIndex, 1);

			this.computeCartTotals();
		}

	}
}