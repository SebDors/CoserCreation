import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common'; // Import CommonModule for ngIf, etc.

@Component({
  selector: 'app-newsletter-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule], // Add ReactiveFormsModule and CommonModule here
  templateUrl: './newsletter-form.component.html',
  styleUrl: './newsletter-form.component.scss'
})
export class NewsletterFormComponent {
  newsletterForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email])
  });
  submissionMessage: string | null = null;
  submissionError: string | null = null;

  constructor(private dataService: DataService) { }

  async onSubmit() {
    this.submissionMessage = null;
    this.submissionError = null;

    if (this.newsletterForm.valid) {
      const email = this.newsletterForm.value.email!;
      try {
        const response = await this.dataService.subscribeToNewsletter(email);
        if (response.success) {
          this.submissionMessage = response.message;
          this.newsletterForm.reset();
        } else {
          this.submissionError = response.message || 'Subscription failed.';
        }
      } catch (error) {
        console.error('Error subscribing to newsletter:', error);
        this.submissionError = 'An error occurred during subscription.';
      }
    } else {
      this.submissionError = 'Veuillez entrer une adresse e-mail valide.';
    }
  }
}
