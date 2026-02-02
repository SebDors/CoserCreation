import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticated = false;
  private apiUrl = '/api/login';

  constructor(private router: Router, private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<void>(this.apiUrl, { username, password }).pipe(
      tap(() => {
        this.isAuthenticated = true;
        this.router.navigate(['/admin/dashboard']);
      }),
      catchError((error) => {
        this.isAuthenticated = false;
        throw error;
      })
    );
  }

  logout(): void {
    this.isAuthenticated = false;
    this.router.navigate(['/admin/login']);
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated;
  }
}
