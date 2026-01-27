import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Product } from '../product.model';
import { DataService } from '../data.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  featuredProducts$!: Observable<Product[]>;

  constructor(private dataService: DataService) {}

  ngOnInit(): void {
    this.featuredProducts$ = this.dataService.getProducts().pipe(
      map(products => products.slice(0, 3))
    );
  }
}
