import { Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private products: any[] = [];
  private SERVER_URL = environment.SERVER_URL;

  constructor(private readonly http: HttpClient) {}

  getOrderById(id: number): Observable<ProductResponseModel[]> {
    return this.http.get<ProductResponseModel[]>(
      this.SERVER_URL + `/orders/${id}`
    );
  }
}

interface ProductResponseModel {
  id: number;
  name: string;
  description: string;
  price: number;
  quantity: number;
  image: string;
}
