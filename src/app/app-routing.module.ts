import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResultPageComponent } from './result-page/result-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { CheckoutPageComponent } from './checkout-page/checkout-page.component';

const routes: Routes = [
  { path: "result", component: ResultPageComponent },
  { path: "home", component: HomePageComponent },
  { path: "checkout", component: CheckoutPageComponent },
  { path: "**", redirectTo: "home" }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
