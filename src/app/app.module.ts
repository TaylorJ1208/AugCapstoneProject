import { NgModule } from '@angular/core';
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
import { UpdateCatalogComponent } from './update-catalog/update-catalog.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    NavComponent,
    ResultPageComponent,
    CategoryNavComponent,
    UserPageComponent,
    StorePurchasesComponent,
    UpdateCatalogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }