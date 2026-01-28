import { Component } from '@angular/core';
import { ProductFormComponent } from '../product-form/product-form.component';
import { ProductListComponent } from '../product-list/product-list.component';

@Component({
  selector: 'app-product-management',
  standalone: true,
  imports: [ProductFormComponent, ProductListComponent],
  template: `
    <h1>Gestion des Créations</h1>
    <div class="row mt-4">
      <div class="col-lg-5 mb-4">
        <app-product-form></app-product-form>
      </div>
      <div class="col-lg-7 mb-4">
        <app-product-list></app-product-list>
      </div>
    </div>
  `
})
export class ProductManagementComponent {}
