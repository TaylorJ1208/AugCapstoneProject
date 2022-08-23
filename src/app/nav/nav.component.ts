import { Component, OnInit } from '@angular/core';
import { NavService } from '../services/nav-service/nav-service/nav.service';
import { ProductService } from '../services/product-service/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {
  searchString: string = "";
  constructor(private navService: NavService, private productService: ProductService, private route: Router) { 

     }

  ngOnInit(): void {

  }

  getProductsByName(name: string) {
    this.productService.getProductByName(name)
        .subscribe((data: any) => {
            this.navService.productSearch = data;
            console.log(this.navService.productSearch);
        })
        this.route.navigate(['result']);
}

}
