import { CartService } from './../../services/cart.service';
import {
  ProductResponse,
  ProductModelServer,
} from './../../models/product.model';
import { ProductService } from '../../services/product.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  constructor(
    private readonly productService: ProductService,
    private readonly cartService: CartService,
    private readonly router: Router
  ) {}

  products: ProductModelServer[] = [];

  ngOnInit(): void {
    this.productService.getAllProducts().subscribe((prods: ProductResponse) => {
      this.products = prods.products;
    });
  }

  selectProduct(id: number): void {
    this.router.navigate(['/products', id]).then();
  }

  addToCart(id: number): void {
    this.cartService.addProductToCart(id);
  }
}
