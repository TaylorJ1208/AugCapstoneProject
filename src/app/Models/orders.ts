import { Product } from "./product";
import { User } from "./user";

export interface Orders {
    orderId: number;
    amount: number;
    orderDate: number;
    status: boolean;
    billingAddress: string;
    shippingAddress: string;
    user: User;
    products: Product[];
    editFieldName?: string;
}