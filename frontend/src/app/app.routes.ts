import { Routes } from '@angular/router';
import { GalleryComponent } from './gallery/gallery.component';
import { ContactFormComponent } from './contact-form/contact-form.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';

export const routes: Routes = [
  { path: '', component: GalleryComponent },
  { path: 'contact', component: ContactFormComponent },
  { path: 'product/:id', component: ProductDetailComponent },
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
  }
];