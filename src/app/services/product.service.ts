import { ProductModelServer } from './../models/product.model';
import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductResponse } from '../models/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private SERVER_URL = environment.SERVER_URL;

  constructor(private readonly http: HttpClient) {}

  getAllProducts(numberOfResults: number = 10): Observable<ProductResponse> {
    return this.http.get<ProductResponse>(`${this.SERVER_URL}/products`, {
      params: {
        limit: numberOfResults.toString(),
      },
    });
  }

  getProductById(id: number): Observable<ProductModelServer> {
    return this.http.get<ProductModelServer>(
      this.SERVER_URL + `/products/${id}`
    );
  }

  getProdutByCat(cat: string): Observable<ProductModelServer[]> {
    return this.http.get<ProductModelServer[]>(
      this.SERVER_URL + `/products/category/${cat}`
    );
  }
}
