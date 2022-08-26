import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResultPageComponent } from './result-page/result-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { CartDetailsComponent } from './cart-details/cart-details.component';



const routes: Routes = [
  { path: "result", component: ResultPageComponent },
  { path: "cart-details", component: CartDetailsComponent },
  { path: "home", component: HomePageComponent },
  { path: "**", redirectTo: "home" }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
