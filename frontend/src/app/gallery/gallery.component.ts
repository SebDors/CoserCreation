import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { Product } from '../product.model';
import { DataService } from '../data.service';

@Component({
  selector: 'app-gallery',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './gallery.component.html',
  styleUrl: './gallery.component.scss'
})
export class GalleryComponent implements OnInit {
  products$!: Observable<Product[]>;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.products$ = this.dataService.getProducts();
  }
}
