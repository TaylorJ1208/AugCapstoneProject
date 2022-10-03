import { Product } from "./product";

export interface Review {
    reviewId: number;
    name: string;
    review: string;
    rating: number;
    product: Product;
    title: string;
}