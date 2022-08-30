import { User } from "./user";

export interface Address {
    addressId: number;
    city: string;
    state: string;
    street: string;
    zipcode: string;
    country: string;
    apartmentNumber: string;
    user: User;
}