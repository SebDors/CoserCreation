import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { Product } from './product.model';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private productsUrl = 'assets/products.json';
  private products = new BehaviorSubject<Product[]>([]);
  products$ = this.products.asObservable();

  constructor(private http: HttpClient) {
    this.http.get<Product[]>(this.productsUrl).pipe(
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

  addProduct(product: Omit<Product, 'id'>): void {
    const currentProducts = this.products.getValue();
    const newId = Math.max(...currentProducts.map(p => p.id)) + 1;
    const newProduct = { ...product, id: newId };
    this.products.next([...currentProducts, newProduct]);
  }

  deleteProduct(id: number): void {
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
