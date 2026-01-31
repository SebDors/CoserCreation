import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap, tap, map } from 'rxjs/operators';
import { Product } from '../product.model';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss']
})
export class ProductDetailComponent implements OnInit {
  product$!: Observable<Product | undefined>;
  selectedImageUrl: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private dataService: DataService
  ) { }

  ngOnInit(): void {
    this.product$ = this.route.paramMap.pipe(
      switchMap(params => {
        const id = Number(params.get('id'));
        return this.dataService.getProductDetails(id);
      }),
      map(item => {
        if (!item) {
          return undefined;
        }
        return {
          id: item.id,
          name: item.title,
          price: item.price,
          description: item.description,
          imageUrls: item.images && item.images.length > 0 ? item.images.map(img => img.imageUrl) : ['assets/images/placeholder.svg'],
          colors: item.colors || []
        };
      }),
      tap(product => {
        if (product && product.imageUrls && product.imageUrls.length > 0) {
          this.selectedImageUrl = product.imageUrls[0];
        }
      })
    );
  }

  selectImage(url: string): void {
    this.selectedImageUrl = url;
  }
}
