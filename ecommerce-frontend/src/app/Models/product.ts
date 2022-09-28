import { Category } from "./categories";
import { Review } from "./Review";
import { Vendor } from "./vendor";

export interface Product {
    productId: number;
    name: string;
    description: string;
    price: number;
    weight: number;
    quantity: number;
    image: string;
    rating: number;
    category: Category;
    vendors: Vendor;
    reviews: Review[];
}