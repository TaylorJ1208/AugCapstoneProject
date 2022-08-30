import { Product } from "./product";
import { User } from "./user";

export interface Orders {
    orderId: number;
    amount: number;
    orderDate: Date;
    status: boolean;
    billingAddress: string;
    shippingAddress: string;
    products: Product[];
    user: User;
}