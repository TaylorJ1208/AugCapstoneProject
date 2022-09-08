import { Injector, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { NavComponent } from './nav/nav.component';
import { ResultPageComponent } from './result-page/result-page.component';
import { CategoryNavComponent } from './category-nav/category-nav.component';
import { UserPageComponent } from './user-page/user-page.component';
import { StorePurchasesComponent } from './store-purchases/store-purchases.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './login/login.component';
import { LoginStatusComponent } from './login-status/login-status.component';
import myAppConfig from './config/my-app-config';
import { OKTA_CONFIG, OktaAuthModule } from '@okta/okta-angular';
import { Router } from '@angular/router';
import { UserAccountDetailsComponent } from './user-account-details/user-account-details.component';
import { UserEditDetailsComponent } from './user-edit-details/user-edit-details.component';
import { UserAdminComponent } from './user-admin/user-admin.component';
import { UserAdminEditComponent } from './user-admin-edit/user-admin-edit.component';
import { UserPurchasesComponent } from './user-purchases/user-purchases.component';
import { CheckoutPageComponent } from './checkout-page/checkout-page.component';
import { CartDetailsComponent } from './cart-details/cart-details.component';
import { CartStatusComponent } from './cart-status/cart-status.component';
import { UpdateCatalogComponent } from './update-catalog/update-catalog.component';

const oktaConfig = Object.assign({
  onAuthRequired: (injector: Injector)=>{
    const router = injector.get(Router)
    router.navigate(['home']);
  }
}, myAppConfig.oidc)


@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    NavComponent,
    ResultPageComponent,
    CategoryNavComponent,
    UserPageComponent,
    StorePurchasesComponent,
    LoginComponent,
    LoginStatusComponent,
    UserAccountDetailsComponent,
    UserEditDetailsComponent,
    UserAdminComponent,
    UserAdminEditComponent,
    UserPurchasesComponent,
    CheckoutPageComponent,
    CartDetailsComponent,
    CartStatusComponent,
    UpdateCatalogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule,
    OktaAuthModule
  ],
  providers: [{provide: OKTA_CONFIG, useValue: oktaConfig}],
  bootstrap: [AppComponent]
})
export class AppModule { }