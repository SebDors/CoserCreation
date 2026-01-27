import { Component } from '@angular/core';
import { ProductFormComponent } from '../product-form/product-form.component';
import { ProductListComponent } from '../product-list/product-list.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [ProductFormComponent, ProductListComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

}
