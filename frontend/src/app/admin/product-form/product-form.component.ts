import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../../data.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent implements OnInit {
  productForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dataService: DataService
  ) { }

  ngOnInit(): void {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      category: ['Couture', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      keywords: [''],
      imageUrl: ['', Validators.required],
      description: ['']
    });
  }

  onGenerateDescription(): void {
    const keywords = this.productForm.get('keywords')?.value;
    // For now, a simple concatenation.
    // The user will be informed that AI integration requires an API key.
    const generatedDescription = `Un produit de qualité, ${keywords}.`;
    this.productForm.get('description')?.setValue(generatedDescription);
  }

  onSubmit(): void {
    if (this.productForm.valid) {
      this.dataService.addProduct(this.productForm.value);
      this.productForm.reset();
    }
  }
}
