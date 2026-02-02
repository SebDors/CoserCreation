import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductManagementComponent } from './product-management/product-management.component';
import { ColorManagementComponent } from './color-management/color-management.component';
import { ProductEditComponent } from './product-edit/product-edit.component';

import { ColorEditComponent } from './color-edit/color-edit.component';
import { authGuard } from '../auth.guard';
// ... other imports

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      { path: '', redirectTo: 'creations', pathMatch: 'full' },
      { path: 'creations/edit/:id', component: ProductEditComponent },
      { path: 'creations', component: ProductManagementComponent },
      { path: 'couleurs/edit/:id', component: ColorEditComponent }, // Add this
      { path: 'couleurs', component: ColorManagementComponent }
    ],
    canActivate: [authGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
