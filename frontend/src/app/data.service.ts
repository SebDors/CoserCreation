import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { Product, Color } from './product.model';
import { ToastrService } from 'ngx-toastr'; // Import ToastrService

// Define a type for the backend item structure for better type safety
interface BackendItem {
  id: number;
  title: string;
  price: number;
  description: string;
  images: { imageUrl: string }[];
  colors: Color[];
}

@Injectable({
  providedIn: 'root'
})
export class DataService {
  // Use a more robust way to define the backend URL, pointing to the API endpoint
  private apiUrl = 'http://localhost:8080/api/items';

  private products = new BehaviorSubject<Product[]>([]);
  products$ = this.products.asObservable();

  constructor(private http: HttpClient, private toastr: ToastrService) { // Inject ToastrService
    this.loadProducts();
  }

  private loadProducts(): void {
    this.http.get<BackendItem[]>(this.apiUrl).pipe(
      map(items => items.map(item => ({
        id: item.id,
        name: item.title, // Map title to name
        price: item.price,
        description: item.description,
        imageUrls: item.images && item.images.length > 0 ? item.images.map(img => img.imageUrl) : ['assets/placeholder.jpg'],
        colors: item.colors || []
      }))),
      tap(products => this.products.next(products))
    ).subscribe({
      error: (err) => this.toastr.error('Erreur lors du chargement des créations', 'Erreur')
    });
  }

  getProducts(): Observable<Product[]> {
    return this.products$;
  }

  getProductById(id: number): Observable<Product | undefined> {
    // This will now fetch the detailed product and map it, or find from the already loaded list
    // For simplicity, we'll find from the list. For a full implementation, a separate API call might be better.
    return this.products$.pipe(
      map(products => products.find(product => product.id === id))
    );
  }

  getColors(): Observable<Color[]> {
    return this.http.get<Color[]>('http://localhost:8080/api/colors');
  }

  addProduct(productData: any): void {
    const itemCreationRequest = {
      title: productData.title,
      price: productData.price,
      description: productData.description,
      colorsId: productData.colors.map((id: string) => parseInt(id, 10)),
      images: [] // Images will be handled later
    };

    this.http.post(this.apiUrl, itemCreationRequest).pipe(
      tap(() => this.loadProducts())
    ).subscribe({
      next: () => this.toastr.success('Création ajoutée avec succès !', 'Succès'),
      error: (err) => this.toastr.error('Erreur lors de l\'ajout de la création', 'Erreur')
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

  subscribeToNewsletter(email: string): Promise<any> {
    console.log('Simulating newsletter subscription for:', email);
    return new Promise(resolve => {
      setTimeout(() => {
        console.log('Newsletter subscription successful for:', email);
        resolve({ success: true, message: 'Subscription successful!' });
      }, 1000); // Simulate network delay
    });
  }

  submitContactForm(name: string, email: string, message: string): Promise<any> {
    console.log('Simulating contact form submission:', { name, email, message });
    return new Promise(resolve => {
      setTimeout(() => {
        console.log('Contact form submission successful for:', { name, email, message });
        resolve({ success: true, message: 'Message sent successfully!' });
      }, 1000); // Simulate network delay
    });
  }
}
