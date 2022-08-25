export interface Orders {
    orderId: number;
    amount: number;
    orderDate: Date;
    status: boolean;
    billingAddress: string;
    shippingAddress: string;
}