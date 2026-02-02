import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Color } from '../../product.model';
import { DataService } from '../../data.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-color-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './color-list.component.html',
  styleUrls: ['./color-list.component.scss']
})
export class ColorListComponent implements OnInit {
  colors$!: Observable<Color[]>;

  constructor(private dataService: DataService, private router: Router) { }

  ngOnInit(): void {
    this.colors$ = this.dataService.getColors();
  }

  onDelete(colorId: number): void {
    this.dataService.deleteColor(colorId).subscribe(() => {
      this.colors$ = this.dataService.getColors(); // Refresh the list
    });
  }

  onEdit(colorId: number): void {
    this.router.navigate(['/admin/dashboard/couleurs/edit', colorId]);
  }
}
