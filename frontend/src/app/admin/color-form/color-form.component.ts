import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DataService } from '../../data.service';

@Component({
  selector: 'app-color-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './color-form.component.html',
  styleUrls: ['./color-form.component.scss']
})
export class ColorFormComponent implements OnInit {
  @Output() colorAdded = new EventEmitter<void>();

  colorForm!: FormGroup;
  imagePreview: string | ArrayBuffer | null = null;
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private dataService: DataService
  ) { }

  ngOnInit(): void {
    this.colorForm = this.fb.group({
      name: ['', Validators.required],
      image: [null, Validators.required]
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
      this.imagePreview = null;
      this.colorForm.patchValue({ image: null });
    }
  }

  onSubmit(): void {
    if (this.colorForm.valid && this.selectedFile) {
      this.dataService.addColor(this.colorForm.value.name, this.selectedFile).subscribe(() => {
        this.colorAdded.emit(); // Notify parent component that a color has been added
        this.colorForm.reset();
        this.imagePreview = null;
        this.selectedFile = null;
      });
    }
  }
}
