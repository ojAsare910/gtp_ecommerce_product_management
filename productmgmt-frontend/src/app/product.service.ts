import { Injectable } from '@angular/core';
import {catchError, Observable, throwError} from 'rxjs';
import { Product } from './product.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import {ProductDao} from "./productdao.model";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) { }

  getProducts(page: number, size: number, searchTerm: string = ''): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    if (searchTerm !== '') {
      params = params.set('searchTerm', searchTerm);
    }
    return this.http.get<any>(this.apiUrl, { params });
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }

  createProduct(product: ProductDao): Observable<ProductDao> {
    return this.http.post<ProductDao>(this.apiUrl, product);
  }

  updateProduct(id: number, product: ProductDao): Observable<ProductDao> {
    return this.http.put<ProductDao>(`${this.apiUrl}/${id}`, product).pipe(
      catchError(error => {
        console.error('Error in updateProduct:', error);
        return throwError('Something went wrong with the update. Please try again.');
      })
    );
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
