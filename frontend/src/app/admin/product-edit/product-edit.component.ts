import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { DataService } from '../../data.service';
import { Color } from '../../product.model';

interface BackendItemDetail {
  id: number;
  title: string;
  price: number;
  description: string;
  images: { id: number, imageUrl: string, altText: string }[];
  colors: Color[];
}

@Component({
  selector: 'app-product-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.scss']
})
export class ProductEditComponent implements OnInit {
  productForm!: FormGroup;
  product: BackendItemDetail | undefined;
  allColors: Color[] = [];
  deletedImageIds: number[] = [];
  newImagePreviews: { preview: string, file: File }[] = [];
  private productId!: number;

  constructor(
    private fb: FormBuilder,
    private dataService: DataService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productId = +id;
    } else {
      this.router.navigate(['/admin/dashboard/creations']);
      return;
    }

    this.productForm = this.fb.group({
      title: ['', Validators.required],
      price: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      description: ['', Validators.required],
      colors: this.fb.array([]),
      existing_images: this.fb.array([]),
      new_images: [null],
      new_image_alts: this.fb.array([])
    });

    this.dataService.getColors().subscribe(colors => {
      this.allColors = colors;
    });

    this.dataService.getProductDetails(this.productId).subscribe(product => {
      this.product = product;
      this.productForm.patchValue({
        title: product.title,
        price: product.price,
        description: product.description
      });

      const colorsFormArray = this.productForm.get('colors') as FormArray;
      product.colors.forEach(color => {
        colorsFormArray.push(new FormControl(color.id));
      });

      const existingImagesFormArray = this.existing_images;
      product.images.forEach(image => {
        existingImagesFormArray.push(this.fb.group({
          id: [image.id],
          altText: [image.altText || ''],
          imageUrl: [image.imageUrl]
        }));
      });
    });
  }

  get existing_images(): FormArray {
    return this.productForm.get('existing_images') as FormArray;
  }

  get new_image_alts(): FormArray {
    return this.productForm.get('new_image_alts') as FormArray;
  }

  isColorSelected(colorId: number): boolean {
    return (this.productForm.get('colors') as FormArray).value.includes(colorId);
  }

  isImageDeleted(imageId: number): boolean {
    return this.deletedImageIds.includes(imageId);
  }

  onColorChange(event: any) {
    const colorsFormArray: FormArray = this.productForm.get('colors') as FormArray;
    const value = parseInt(event.target.value, 10);

    if (event.target.checked) {
      colorsFormArray.push(new FormControl(value));
    } else {
      const index = colorsFormArray.controls.findIndex(x => x.value === value);
      if (index !== -1) {
        colorsFormArray.removeAt(index);
      }
    }
  }

  onFileChange(event: any) {
    this.newImagePreviews = [];
    this.new_image_alts.clear();
    if (event.target.files && event.target.files.length > 0) {
      const files = event.target.files;
      this.productForm.patchValue({ new_images: files });

      for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();
        reader.onload = () => {
          this.newImagePreviews.push({ preview: reader.result as string, file: file });
          this.new_image_alts.push(this.fb.control(''));
        };
        reader.readAsDataURL(file);
      }
    }
  }

  onDeleteImage(imageId: number): void {
    if (!this.isImageDeleted(imageId)) {
      this.deletedImageIds.push(imageId);
    }
  }

  onSave(): void {
    if (this.productForm.invalid) {
      return;
    }
    this.dataService.updateProduct(this.productId, this.productForm.value, this.deletedImageIds);
    this.router.navigate(['/admin/dashboard/creations']);
  }

  onCancel(): void {
    this.router.navigate(['/admin/dashboard/creations']);
  }
}