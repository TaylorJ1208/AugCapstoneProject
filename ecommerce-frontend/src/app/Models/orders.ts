import { Product } from "./product";
import { User } from "./user";

export interface Orders {
    orderId: number;
    amount: number;
    orderDate: Date;
    status: boolean;
    billingAddress: string;
    shippingAddress: string;
    user: User;
    products: Product[];
    editFieldName?: string;

}