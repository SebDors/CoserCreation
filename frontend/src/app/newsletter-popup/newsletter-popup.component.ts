import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { DataService } from '../data.service';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-newsletter-popup',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './newsletter-popup.component.html',
  styleUrls: ['./newsletter-popup.component.scss']
})
export class NewsletterPopupComponent implements OnInit {
  newsletterForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dataService: DataService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.newsletterForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit(): void {
    if (this.newsletterForm.valid) {
      this.dataService.addClient(this.newsletterForm.value.email).subscribe({
        next: () => {
          this.toastr.success('Vous êtes bien inscrit à notre newsletter !', 'Succès');
          this.newsletterForm.reset();
        },
        error: (err) => {
          if (err.status === 409) {
            this.toastr.error('Vous êtes déjà inscrit à la newsletter, si cela est une erreur, veuillez nous contacter', 'Erreur');
          } else {
            this.toastr.error('Erreur lors de l\'inscription à la newsletter', 'Erreur');
          }
        }
      });
    }
  }
}