import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResultPageComponent } from './result-page/result-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { UserPageComponent } from './user-page/user-page.component';
import { UserAccountDetailsComponent } from './user-account-details/user-account-details.component';
import { UserEditDetailsComponent } from './user-edit-details/user-edit-details.component';
import { UserAdminComponent } from './user-admin/user-admin.component';
import { UserAdminEditComponent } from './user-admin-edit/user-admin-edit.component';


const routes: Routes = [
  { path: "result", component: ResultPageComponent },
  { path: "home", component: HomePageComponent },
  { path: "user/details", component: UserPageComponent },
  { path: "user/accountDetails", component: UserAccountDetailsComponent},
  { path: "user/editDetails", component: UserEditDetailsComponent},
  { path: "user/purchases", component: UserPageComponent },
  { path: "user/cart", component: UserPageComponent },
  { path: "user/admin/store-purchases", component: UserPageComponent },
  { path: "user/admin/details", component: UserAdminComponent},
  { path: `user/admin/edit/:userId`, component: UserAdminEditComponent},
  { path: "**", redirectTo: "home" }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
