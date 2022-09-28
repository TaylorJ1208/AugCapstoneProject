import { Product } from "./product";

export interface Review {
    reviewId: number;
    name: String;
    review: String;
    rating: number;
    product: Product;
    title: string;
}