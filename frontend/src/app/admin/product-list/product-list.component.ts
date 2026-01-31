import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Product } from '../../product.model';
import { DataService } from '../../data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  products$!: Observable<Product[]>;

  constructor(private dataService: DataService, private router: Router) { }

  ngOnInit(): void {
    this.products$ = this.dataService.getProducts();
  }

  onDelete(productId: number): void {
    this.dataService.deleteProduct(productId);
  }

  onEdit(productId: number): void {
    this.router.navigate(['/admin/dashboard/creations/edit', productId]);
  }
}
