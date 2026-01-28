import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { Product, Color } from './product.model';

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

  constructor(private http: HttpClient) {
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
    ).subscribe();
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
    // This method needs to be updated to handle multipart/form-data for the image upload.
    // The backend also needs a corresponding endpoint.
    const itemCreationRequest = {
      title: productData.title,
      price: productData.price,
      description: productData.description,
      // The backend expects a list of integers for colorsId.
      colorsId: productData.colors.map((id: string) => parseInt(id, 10)),
      // The image file (productData.image) needs to be sent via FormData.
      images: [] // Placeholder for now
    };

    console.log('Data to be sent to backend (image upload not included):', itemCreationRequest);
    console.warn('addProduct backend request not fully implemented yet.');

    // TODO: Implement a multipart/form-data request to the backend.
    // Example:
    // const formData = new FormData();
    // formData.append('item', new Blob([JSON.stringify(itemCreationRequest)], { type: "application/json" }));
    // formData.append('imageFile', productData.image);
    // this.http.post<any>('/api/items', formData).subscribe(() => this.loadProducts());
  }

  deleteProduct(id: number): void {
    // This should be updated to send a delete request to the backend.
    console.warn('deleteProduct not implemented with backend yet.');
    const currentProducts = this.products.getValue();
    const updatedProducts = currentProducts.filter(product => product.id !== id);
    this.products.next(updatedProducts);
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
