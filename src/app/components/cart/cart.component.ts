import { CartService } from './../../services/cart.service';
import { CartModelServer } from './../../models/cart.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
})
export class CartComponent implements OnInit {
  cartData: CartModelServer;
  cartTotal: number;
  subTotal: number;

  constructor(protected readonly cartService: CartService) {}

  ngOnInit(): void {
    this.cartService.cartData$.subscribe(
      (data: CartModelServer) => (this.cartData = data)
    );
    this.cartService.cartTotal$.subscribe((total) => (this.cartTotal = total));
  }

  changeQuantity(index: number, increase: boolean): void {
    this.cartService.updateCartItem(index, increase);
  }
}
