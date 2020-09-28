import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-thankyou',
  templateUrl: './thankyou.component.html',
  styleUrls: ['./thankyou.component.scss'],
})
export class ThankyouComponent implements OnInit {
  message: string;
  orderId: number;
  products: ProductResponseModel[];
  cartTotal: number;

  constructor(
    private readonly router: Router,
    private readonly orderService: OrderService
  ) {
    const navigation = this.router.getCurrentNavigation();
    const state = navigation.extras.state as {
      message: string;
      products: ProductResponseModel[];
      orderId: number;
      total: number;
    };

    this.message = state.message;
    this.products = state.products;
    this.orderId = state.orderId;
    this.cartTotal = state.total;
  }

  ngOnInit(): void {}
}

interface ProductResponseModel {
  id: number;
  name: string;
  description: string;
  price: number;
  quantity: number;
  image: string;
}
