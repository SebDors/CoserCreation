import { Component, OnInit } from '@angular/core';
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

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.products$ = this.dataService.getProducts();
  }

  onDelete(productId: number): void {
    this.dataService.deleteProduct(productId);
  }
}
