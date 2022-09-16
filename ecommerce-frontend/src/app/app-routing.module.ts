import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResultPageComponent } from './result-page/result-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { UserPageComponent } from './user-page/user-page.component';
import { UserAccountDetailsComponent } from './user-account-details/user-account-details.component';
import { UserEditDetailsComponent } from './user-edit-details/user-edit-details.component';
import { UserAdminComponent } from './user-admin/user-admin.component';
import { UserAdminEditComponent } from './user-admin-edit/user-admin-edit.component';
import { LoginComponent } from './login/login.component';
import { OktaCallbackComponent } from '@okta/okta-angular';
import { CheckoutPageComponent } from './checkout-page/checkout-page.component';
import { CartDetailsComponent } from './cart-details/cart-details.component';

const routes: Routes = [
  { path: "result", component: ResultPageComponent },
  { path: "login",component: LoginComponent },
  { path: "login/callback", component: OktaCallbackComponent },
  { path: "home", component: HomePageComponent },
  { path: "user/details", component: UserPageComponent },
  { path: "user/accountDetails", component: UserAccountDetailsComponent},
  { path: "user/editDetails", component: UserEditDetailsComponent},
  { path: "user/purchases", component: UserPageComponent },
  { path: "user/cart", component: CartDetailsComponent },
  { path: "user/cart/checkout", component: CheckoutPageComponent },
  { path: "user/admin/store-purchases", component: UserPageComponent },
  { path: "catalog/update-products", component: UserPageComponent},
  { path: "catalog/update-categories", component: UserPageComponent},
  { path: "user/admin/details", component: UserAdminComponent},
  { path: `user/admin/edit/:userId`, component: UserAdminEditComponent},
  { path: `user/editDetails/:userId`, component: UserEditDetailsComponent},
  { path: "**", redirectTo: "login" }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }