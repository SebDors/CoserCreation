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

  constructor(
    private fb: FormBuilder,
    private dataService: DataService
  ) { }

  ngOnInit(): void {
    this.productForm = this.fb.group({
      title: ['', Validators.required],
      price: ['', [Validators.required, Validators.pattern(/^\d+(\.\d{1,2})?$/)]],
      description: ['', Validators.required],
      image: [null],
      colors: this.fb.array([])
    });

    this.dataService.getColors().subscribe(colors => {
      this.colors = colors;
    });
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.productForm.patchValue({
        image: file
      });
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
    }
  }
}
