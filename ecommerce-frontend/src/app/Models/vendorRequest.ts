import { Product } from "./product";
import { Vendor } from "./vendor";

export interface VendorRequest{
    id:number;
    product:Product;
    quantityrequested:number;
    requestdate:Date;
    vendor:Vendor;
    status:string;
}