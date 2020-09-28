import { CartModelServer } from './../../models/cart.model';
import { OrderService } from './../../services/order.service';
import { CartService } from './../../services/cart.service';
import { CartComponent } from './../cart/cart.component';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
})
export class CheckoutComponent implements OnInit {
  cartTotal: number;
  cartData: CartModelServer;

  constructor(
    private readonly cartService: CartService,
    private readonly orderService: OrderService,
    private readonly router: Router,
    private readonly spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.cartService.cartData$.subscribe((data) => (this.cartData = data));
    this.cartService.cartTotal$.subscribe((total) => (this.cartTotal = total));
  }

  onCheckout(): void {
    this.spinner.show();
    this.cartService.checkoutFromCart(2);
  }
}
