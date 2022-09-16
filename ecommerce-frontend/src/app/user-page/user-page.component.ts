import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.scss']
})
export class UserPageComponent implements OnInit {
  username: any;
  url = this.router.url
  constructor(private router: Router) { }

  ngOnInit(): void {
    this.url = this.router.url;
  }
}
