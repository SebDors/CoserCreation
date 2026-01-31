import { Component } from '@angular/core';
import { ProductFormComponent } from '../product-form/product-form.component';
import { ProductListComponent } from '../product-list/product-list.component';

@Component({
  selector: 'app-product-management',
  standalone: true,
  imports: [ProductFormComponent, ProductListComponent],
  template: `
    <h1>Gestion des Créations</h1>

    <div class="accordion mt-4" id="creationsAccordion">
      <div class="accordion-item">
        <h2 class="accordion-header" id="headingOne">
          <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
            Toutes les créations
          </button>
        </h2>
        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#creationsAccordion">
          <div class="accordion-body">
            <app-product-list></app-product-list>
          </div>
        </div>
      </div>
      <div class="accordion-item">
        <h2 class="accordion-header" id="headingTwo">
          <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
            Ajouter une nouvelle création
          </button>
        </h2>
        <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#creationsAccordion">
          <div class="accordion-body">
            <app-product-form></app-product-form>
          </div>
        </div>
      </div>
    </div>
  `
})
export class ProductManagementComponent {}
