import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from '../../data.service';
import { Color } from '../../product.model';

@Component({
  selector: 'app-color-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './color-edit.component.html',
  styleUrls: ['./color-edit.component.scss']
})
export class ColorEditComponent implements OnInit {
  colorForm!: FormGroup;
  imagePreview: string | ArrayBuffer | null = null;
  selectedFile: File | null = null;
  currentImage: string | undefined; // This type is correct

  private colorId!: number;

  constructor(
    private fb: FormBuilder,
    private dataService: DataService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.colorId = +id;
    } else {
      this.router.navigate(['/admin/dashboard/couleurs']);
      return;
    }

    this.colorForm = this.fb.group({
      name: ['', Validators.required],
      image: [null] // Image is optional for update
    });

    this.dataService.getColorDetails(this.colorId).subscribe(color => {
      this.colorForm.patchValue({
        name: color.name
      });
      this.currentImage = color.image;
      this.imagePreview = color.image ?? null; // Fix: Handle undefined to null conversion
    });
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      this.colorForm.patchValue({ image: file });

      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);
    } else {
      this.selectedFile = null;
      this.imagePreview = this.currentImage ?? null; // Fix: Revert to current image or null
      this.colorForm.patchValue({ image: null });
    }
  }

  onSave(): void {
    if (this.colorForm.invalid) {
      return;
    }
    this.dataService.updateColor(this.colorId, this.colorForm.value.name, this.selectedFile ?? undefined).subscribe(() => { // Fix: Pass undefined explicitly
      this.router.navigate(['/admin/dashboard/couleurs']);
    });
  }

  onCancel(): void {
    this.router.navigate(['/admin/dashboard/couleurs']);
  }
}
