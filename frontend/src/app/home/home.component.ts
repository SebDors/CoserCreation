import { Component } from '@angular/core';
import { GalleryComponent } from '../gallery/gallery.component';
import { NewsletterFormComponent } from '../newsletter-form/newsletter-form.component';
import { ContactFormComponent } from '../contact-form/contact-form.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [GalleryComponent, NewsletterFormComponent, ContactFormComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
