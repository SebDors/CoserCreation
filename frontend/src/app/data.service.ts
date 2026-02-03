import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, map, tap, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product, Color } from './product.model';
import { ToastrService } from 'ngx-toastr';

interface BackendItem {
  id: number;
  title: string;
  price: number;
  description: string;
  images: { imageUrl: string }[];
  colors: Color[];
}

interface BackendItemDetail {
  id: number;
  title: string;
  price: number;
  description: string;
  images: { id: number, imageUrl: string, altText: string }[];
  colors: Color[];
}

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private apiUrl = 'http://74.161.36.149:8080/api/items';
  private colorsApiUrl = 'http://74.161.36.149:8080/api/colors';

  private products = new BehaviorSubject<Product[]>([]);
  products$ = this.products.asObservable();

  constructor(private http: HttpClient, private toastr: ToastrService) {
    this.loadProducts();
  }

  getItems(sortBy: string, sortDirection: string, limit?: number): Observable<Product[]> {
    let params: any = {
      sortBy: sortBy,
      sortDirection: sortDirection
    };
    if (limit) {
      params.limit = limit;
    }

    return this.http.get<BackendItem[]>(this.apiUrl, { params }).pipe(
      map(items => items.map(item => ({
        id: item.id,
        name: item.title,
        price: item.price,
        description: item.description,
        imageUrls: item.images && item.images.length > 0 ? item.images.map(img => img.imageUrl) : ['assets/images/placeholder.svg'],
        colors: item.colors || []
      }))),
      catchError((err) => {
        this.toastr.error('Erreur lors du chargement des créations', 'Erreur');
        return throwError(() => err);
      })
    );
  }

  private loadProducts(): void {
    this.getItems('id', 'asc').pipe(
      tap(products => this.products.next(products))
    ).subscribe();
  }

  getProducts(): Observable<Product[]> {
    return this.products$;
  }

  getProductById(id: number): Observable<Product | undefined> {
    return this.products$.pipe(
      map(products => products.find(product => product.id === id))
    );
  }

  getProductDetails(id: number): Observable<BackendItemDetail> {
    return this.http.get<BackendItemDetail>(`${this.apiUrl}/${id}`);
  }

  // --- Product CRUD (Existing) ---
  addProduct(productData: any): void {
    const formData = new FormData();

    if (productData.images) {
      for (let i = 0; i < productData.images.length; i++) {
        formData.append('images', productData.images[i]);
      }
    }

    if (productData.image_alts) {
      for (let i = 0; i < productData.image_alts.length; i++) {
        formData.append('altTexts', productData.image_alts[i]);
      }
    }

    const itemCreationDTO = {
      title: productData.title,
      price: productData.price,
      description: productData.description,
      colorsId: productData.colors.map((id: string) => parseInt(id, 10))
    };
    formData.append('item', new Blob([JSON.stringify(itemCreationDTO)], { type: 'application/json' }));

    this.http.post(this.apiUrl, formData).pipe(
      tap(() => this.loadProducts())
    ).subscribe({
      next: () => this.toastr.success('Création ajoutée avec succès !', 'Succès'),
      error: (err) => this.toastr.error('Erreur lors de l\'ajout de la création', 'Erreur')
    });
  }

  updateProduct(id: number, productData: any, deletedImageIds: number[]): void {
    const formData = new FormData();

    if (productData.new_images) {
      for (let i = 0; i < productData.new_images.length; i++) {
        formData.append('new_images', productData.new_images[i]);
      }
    }

    if (productData.new_image_alts) {
      for (let i = 0; i < productData.new_image_alts.length; i++) {
        formData.append('new_image_alts', productData.new_image_alts[i]);
      }
    }

    const itemUpdateDTO = {
      title: productData.title,
      price: productData.price,
      description: productData.description,
      colorsId: productData.colors,
      existingImages: productData.existing_images,
      deletedImageIds: deletedImageIds
    };

    formData.append('item', new Blob([JSON.stringify(itemUpdateDTO)], { type: 'application/json' }));

    this.http.put(`${this.apiUrl}/${id}`, formData).pipe(
      tap(() => this.loadProducts())
    ).subscribe({
      next: () => this.toastr.success('Création mise à jour avec succès !', 'Succès'),
      error: (err) => this.toastr.error('Erreur lors de la mise à jour', 'Erreur')
    });
  }

  deleteProduct(id: number): void {
    this.http.delete(`${this.apiUrl}/${id}`).pipe(
      tap(() => this.loadProducts())
    ).subscribe({
      next: () => this.toastr.success('Création supprimée avec succès !', 'Succès'),
      error: (err) => this.toastr.error('Erreur lors de la suppression de la création', 'Erreur')
    });
  }

  // --- Color CRUD (New) ---
  getColors(): Observable<Color[]> {
    return this.http.get<Color[]>(this.colorsApiUrl);
  }

  addColor(name: string, image: File): Observable<any> {
    const formData = new FormData();
    formData.append('name', name);
    formData.append('image', image);
    return this.http.post(this.colorsApiUrl, formData).pipe(
      tap(() => this.toastr.success('Couleur ajoutée avec succès !', 'Succès')),
      catchError((err) => {
        this.toastr.error('Erreur lors de l\'ajout de la couleur', 'Erreur');
        return throwError(() => err);
      })
    );
  }

  updateColor(id: number, name: string, image?: File): Observable<any> {
    const formData = new FormData();
    formData.append('name', name);
    if (image) {
      formData.append('image', image);
    }
    return this.http.put(`${this.colorsApiUrl}/${id}`, formData).pipe(
      tap(() => this.toastr.success('Couleur mise à jour avec succès !', 'Succès')),
      catchError((err) => {
        this.toastr.error('Erreur lors de la mise à jour de la couleur', 'Erreur');
        return throwError(() => err);
      })
    );
  }

  deleteColor(id: number): Observable<any> {
    return this.http.delete(`${this.colorsApiUrl}/${id}`).pipe(
      tap(() => this.toastr.success('Couleur supprimée avec succès !', 'Succès')),
      catchError((err) => {
        this.toastr.error('Erreur lors de la suppression de la couleur', 'Erreur');
        return throwError(() => err);
      })
    );
  }

  getColorDetails(id: number): Observable<Color> {
    return this.http.get<Color>(`${this.colorsApiUrl}/${id}`);
  }

  // --- Other methods (Existing) ---
  subscribeToNewsletter(email: string): Promise<any> {
    console.log('Simulating newsletter subscription for:', email);
    return new Promise(resolve => {
      setTimeout(() => {
        console.log('Newsletter subscription successful for:', email);
        resolve({ success: true, message: 'Subscription successful!' });
      }, 1000);
    });
  }

  submitContactForm(name: string, email: string, message: string): Promise<any> {
    console.log('Simulating contact form submission:', { name, email, message });
    return new Promise(resolve => {
      setTimeout(() => {
        console.log('Contact form submission successful for:', { name, email, message });
        resolve({ success: true, message: 'Message sent successfully!' });
      }, 1000);
    });
  }
}