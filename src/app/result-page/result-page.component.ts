import { Component, OnInit } from '@angular/core';
import { Product } from '../Models/product';
import { NavService } from '../services/nav-service/nav-service/nav.service';
@Component({
  selector: 'app-result-page',
  templateUrl: './result-page.component.html',
  styleUrls: ['./result-page.component.scss']
})
export class ResultPageComponent implements OnInit {
  products: Product[] = [];
  constructor(private navService: NavService) { }

  ngOnInit(): void {
      this.navService.cast.subscribe((products) => {
        this.products = products;
      });
      }
  }

  


