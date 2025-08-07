import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ProductListComponent } from './components/product-list/product-list.component';
import { ProductFormComponent } from './components/product-form/product-form.component';
import { CartComponent } from './components/cart/cart.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { PurchasesComponent } from './components/purchases/purchases.component';
import { NgModule } from '@angular/core';
import { RegisterComponent } from './components/register/register.component';
import { AuthGuard } from './guards/auth.guard';
import { AdminWishlistsComponent } from './components/admin-wishlists/admin-wishlists.component';
import { AdminPurchasesComponent } from './components/admin-purchases/admin-purchases.component';
import { LandingComponent } from './components/landing/landing.component';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'products', component: ProductListComponent, canActivate: [AuthGuard] },
  { path: 'product-form', component: ProductFormComponent, canActivate: [AuthGuard] },
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard] },
  { path: 'wishlist', component: WishlistComponent, canActivate: [AuthGuard] },
  { path: 'purchases', component: PurchasesComponent, canActivate: [AuthGuard] },
  { path: 'admin-wishlists', component: AdminWishlistsComponent, canActivate: [AuthGuard] },
  { path: 'admin-purchases', component: AdminPurchasesComponent, canActivate: [AuthGuard] }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
