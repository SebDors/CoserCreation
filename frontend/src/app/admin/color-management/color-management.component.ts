import { Component } from '@angular/core';
import { ColorListComponent } from '../color-list/color-list.component';
import { ColorFormComponent } from '../color-form/color-form.component';
import { RouterOutlet } from '@angular/router'; // Import RouterOutlet for nested routes

@Component({
  selector: 'app-color-management',
  standalone: true,
  imports: [ColorListComponent, ColorFormComponent, RouterOutlet],
  templateUrl: './color-management.component.html',
  styleUrls: ['./color-management.component.scss'] // Assuming you might want specific styles
})
export class ColorManagementComponent {
  onColorAdded(): void {
    // Logic to refresh the color list if needed.
    // For now, the color-list component's Observable handles refreshing on its own,
    // but in a more complex scenario, you might want to call a method on the list component.
    console.log('Color added, refreshing list...');
  }
}