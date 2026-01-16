import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common'; // Import CommonModule for ngIf, etc.

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule], // Add ReactiveFormsModule and CommonModule here
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.scss'
})
export class ContactFormComponent {
  contactForm = new FormGroup({
    name: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    message: new FormControl('', Validators.required)
  });
  submissionMessage: string | null = null;
  submissionError: string | null = null;

  constructor(private dataService: DataService) { }

  async onSubmit() {
    this.submissionMessage = null;
    this.submissionError = null;

    if (this.contactForm.valid) {
      const { name, email, message } = this.contactForm.value;
      try {
        const response = await this.dataService.submitContactForm(name!, email!, message!);
        if (response.success) {
          this.submissionMessage = response.message;
          this.contactForm.reset();
        } else {
          this.submissionError = response.message || 'Submission failed.';
        }
      } catch (error) {
        console.error('Error submitting contact form:', error);
        this.submissionError = 'An error occurred during submission.';
      }
    } else {
      this.submissionError = 'Veuillez remplir tous les champs du formulaire de contact avec des informations valides.';
    }
  }
}
