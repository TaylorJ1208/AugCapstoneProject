import { Injectable } from '@angular/core';
import { Product } from 'src/app/Models/product';

@Injectable({
  providedIn: 'root'
})
export class NavService {
  searchString: string = "";
  productSearch: Product[] = [];
  constructor() { }
}
