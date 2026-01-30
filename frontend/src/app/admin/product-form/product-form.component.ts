import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../../data.service';
import { CommonModule } from '@angular/common';
import { Color } from '../../product.model';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent implements OnInit {
  productForm!: FormGroup;
  colors: Color[] = [];
  imagePreviews: { preview: string, file: File }[] = [];

  constructor(
    private fb: FormBuilder,
    private dataService: DataService
  ) { }

  ngOnInit(): void {
    this.productForm = this.fb.group({
      title: ['', Validators.required],
      price: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      description: ['', Validators.required],
      images: [null],
      image_alts: this.fb.array([]),
      colors: this.fb.array([])
    });

    this.dataService.getColors().subscribe(colors => {
      this.colors = colors;
    });
  }

  get image_alts(): FormArray {
    return this.productForm.get('image_alts') as FormArray;
  }

  onFileChange(event: any) {
    this.imagePreviews = [];
    this.image_alts.clear();
    if (event.target.files && event.target.files.length > 0) {
      const files = event.target.files;
      this.productForm.patchValue({
        images: files
      });

      for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();
        reader.onload = () => {
          this.imagePreviews.push({ preview: reader.result as string, file: file });
          this.image_alts.push(this.fb.control(''));
        };
        reader.readAsDataURL(file);
      }
    }
  }

  onColorChange(event: any) {
    const colorsArray: FormArray = this.productForm.get('colors') as FormArray;

    if (event.target.checked) {
      colorsArray.push(new FormControl(event.target.value));
    } else {
      let i: number = 0;
      colorsArray.controls.forEach((item: any) => {
        if (item.value == event.target.value) {
          colorsArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

  onSubmit(): void {
    if (this.productForm.valid) {
      console.log(this.productForm.value);
      // Logic will be updated later to include images and colors
      this.dataService.addProduct(this.productForm.value);
      this.productForm.reset();
      this.image_alts.clear();
      this.imagePreviews = []; // Reset previews
    }
  }
}
