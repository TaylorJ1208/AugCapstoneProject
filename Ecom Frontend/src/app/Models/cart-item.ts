import { Product } from "./product";

export class CartItem {
	
	productId: number;
	name: string;
	image: string;
	price: number;
	quantity: number;
	
	constructor(product: Product){
		this.productId=product.productId;
		this.name=product.name;
		this.image=product.image;
		this.price=product.price;
		this.quantity=1;
		
	}
	

}