import { Address } from "./address";

export interface User {
    userId: number;
    firstName: string;
    lastName: string;
    email: string;
    userName: string;
    password: string;
    ssn: string;
    contact: string;
    //TODO UNCOMMENT WHEN CLASSES ARE IMPLEMENTED
    //orders: Order[];
    //roles: Role[];
    //userCart: Cart[]
}