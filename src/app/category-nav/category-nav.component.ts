import { Component, OnInit } from '@angular/core';
import { NavService } from '../services/nav-service/nav-service/nav.service';

@Component({
  selector: 'app-category-nav',
  templateUrl: './category-nav.component.html',
  styleUrls: ['./category-nav.component.scss']
})
export class CategoryNavComponent implements OnInit {

  constructor(private navService: NavService) { }

  ngOnInit(): void {
  }

  filterCategory(e: Event): void {
    this.navService.getProductByCategory((e.target as HTMLButtonElement).value);
  }

}
