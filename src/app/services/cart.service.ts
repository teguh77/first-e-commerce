import { OrderService } from './order.service';
import { environment } from './../../environments/environment';
import { ProductModelServer } from './../models/product.model';
import { NavigationExtras, Router } from '@angular/router';
import { CartModelPublic, CartModelServer } from './../models/cart.model';
import { ProductService } from './../services/product.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private SERVER_URL = environment.SERVER_URL;
  // data variable to store data information on the client local storage
  private cartDataClient: CartModelPublic = {
    total: 0,
    prodData: [
      {
        id: 0,
        incart: 0,
      },
    ],
  };

  // data variable to store data information on the server
  private cartDataServer: CartModelServer = {
    total: 0,
    data: [
      {
        product: undefined,
        numInCart: 0,
      },
    ],
  };

  /* observable for the component  to subscribe */
  cartTotal$ = new BehaviorSubject<number>(0);
  cartData$ = new BehaviorSubject<CartModelServer>(this.cartDataServer);

  constructor(
    private readonly http: HttpClient,
    private readonly orderService: OrderService,
    private readonly productService: ProductService,
    private readonly router: Router,
    private readonly toast: ToastrService,
    private readonly spinner: NgxSpinnerService
  ) {
    this.cartTotal$.next(this.cartDataServer.total);
    this.cartData$.next(this.cartDataServer);

    // get information from local storage {if any}

    const info: CartModelPublic = JSON.parse(localStorage.getItem('cart'));

    if (info && info.prodData[0].incart !== 0) {
      this.cartDataClient = info;

      this.cartDataClient.prodData.forEach((p) => {
        this.productService
          .getProductById(p.id)
          .subscribe((actualProductInfo: ProductModelServer) => {
            // check if cart data server already has some entry or not
            if (this.cartDataServer.data[0].numInCart === 0) {
              this.cartDataServer.data[0].numInCart = p.incart;
              this.cartDataServer.data[0].product = actualProductInfo;

              this.calculateTotal();
              this.cartDataClient.total = this.cartDataServer.total;
              localStorage.setItem('cart', JSON.stringify(this.cartDataClient));
            } else {
              // cart data server already has some entry in it
              this.cartDataServer.data.push({
                numInCart: p.incart,
                product: actualProductInfo,
              });
              this.calculateTotal();
              this.cartDataClient.total = this.cartDataServer.total;
              localStorage.setItem('cart', JSON.stringify(this.cartDataClient));
            }

            this.cartData$.next({ ...this.cartDataServer });
          });
      });
    }
  }

  /********************************* end of constructor body */

  addProductToCart(id: number, quantity?: number): void {
    this.productService.getProductById(id).subscribe((prod) => {
      // 1. IF THE CART IS EMPTY
      if (this.cartDataServer.data[0].product === undefined) {
        this.cartDataServer.data[0].product = prod;
        this.cartDataServer.data[0].numInCart =
          quantity !== undefined ? quantity : 1;

        this.cartDataClient.prodData[0].incart = this.cartDataServer.data[0].numInCart;
        this.cartDataClient.prodData[0].id = prod.id;

        this.calculateTotal();
        this.cartDataClient.total = this.cartDataServer.total;
        localStorage.setItem('cart', JSON.stringify(this.cartDataClient));
        this.cartData$.next({ ...this.cartDataServer });
        this.toast.success(`${prod.name} added to the cart`, 'Product Added', {
          timeOut: 1500,
          progressBar: true,
          progressAnimation: 'decreasing',
          positionClass: 'toast-top-right',
        });
      } else {
        // 2. IF THE CART IS NOT EMPTY
        const index = this.cartDataServer.data.findIndex(
          (p) => p.product.id === prod.id
        ); // -1 or a positive value

        // a. if that item is already in the cart => index is positive value
        if (index !== -1) {
          if (quantity !== undefined && quantity <= prod.quantity) {
            this.cartDataServer.data[index].numInCart =
              this.cartDataServer.data[index].numInCart < prod.quantity
                ? quantity
                : prod.quantity;
          } else {
            this.cartDataServer.data[index].numInCart < prod.quantity
              ? this.cartDataServer.data[index].numInCart++
              : prod.quantity;
          } // END OF ELSE

          this.cartDataClient.prodData[index].incart = this.cartDataServer.data[
            index
          ].numInCart;
          // set loca storage setelah menambah jumlah product di cart
          this.calculateTotal();
          this.cartDataClient.total = this.cartDataServer.total;
          localStorage.setItem('cart', JSON.stringify(this.cartDataClient));
          this.toast.info(
            `${prod.name} quantity updated in the cart`,
            'Product Updated',
            {
              timeOut: 1500,
              progressBar: true,
              progressAnimation: 'decreasing',
              positionClass: 'toast-top-right',
            }
          );

          // if product not in the cart array => index -1
        } else {
          this.cartDataServer.data.push({
            numInCart: 1,
            product: prod,
          });
          this.cartDataClient.prodData.push({
            incart: 1,
            id: prod.id,
          });

          this.toast.success(
            `${prod.name} added to the cart`,
            'Product Added',
            {
              timeOut: 1500,
              progressBar: true,
              progressAnimation: 'decreasing',
              positionClass: 'toast-top-right',
            }
          );
          this.calculateTotal();
          this.cartDataClient.total = this.cartDataServer.total;
          localStorage.setItem('cart', JSON.stringify(this.cartDataClient));
          this.cartData$.next({ ...this.cartDataServer });
        } // END OF ELSE
      } // END OF ELSE
    });
  }

  updateCartItem(index: number, increase: boolean): void {
    const data = this.cartDataServer.data[index];

    if (increase) {
      data.numInCart < data.product.quantity
        ? data.numInCart++
        : data.product.quantity;
      this.cartDataClient.prodData[index].incart = data.numInCart;

      this.calculateTotal();
      this.cartDataClient.total = this.cartDataServer.total;
      localStorage.setItem('cart', JSON.stringify(this.cartDataClient));
      this.cartData$.next({ ...this.cartDataServer });
    } else {
      data.numInCart--;

      if (data.numInCart < 1) {
        this.deleteProductFromCart(index);
        this.cartData$.next({ ...this.cartDataServer });
      } else {
        this.cartDataClient.prodData[index].incart = data.numInCart;

        this.calculateTotal();
        this.cartDataClient.total = this.cartDataServer.total;
        localStorage.setItem('cart', JSON.stringify(this.cartDataClient));
        this.cartData$.next({ ...this.cartDataServer });
      }
    }
  }

  deleteProductFromCart(index: number): void {
    if (window.confirm('Are you sure want to remove the item?')) {
      this.cartDataServer.data.splice(index, 1);
      this.cartDataClient.prodData.splice(index, 1);

      this.calculateTotal();
      this.cartDataClient.total = this.cartDataServer.total;

      if (this.cartDataClient.total === 0) {
        this.cartDataClient = { total: 0, prodData: [{ id: 0, incart: 0 }] };
        localStorage.setItem('cart', JSON.stringify(this.cartDataClient));
      } else {
        localStorage.setItem('cart', JSON.stringify(this.cartDataClient));
      }

      if (this.cartDataServer.total === 0) {
        this.cartDataServer = {
          total: 0,
          data: [{ numInCart: 0, product: undefined }],
        };
        this.cartData$.next({ ...this.cartDataServer });
      } else {
        this.cartData$.next({ ...this.cartDataServer });
      }
    } else {
      // If user click cancel

      return;
    }
  }

  private calculateTotal(): void {
    let total = 0;
    this.cartDataServer.data.forEach((p) => {
      const { numInCart } = p;
      const { price } = p.product;

      total += numInCart * price;
    });

    this.cartDataServer.total = total;
    this.cartTotal$.next(this.cartDataServer.total);
  }

  calculateSubTotal(index: number): number {
    let subTotal = 0;
    const p = this.cartDataServer.data[index];
    subTotal = p.product.price * p.numInCart;
    return subTotal;
  }

  checkoutFromCart(userId: number): void {
    this.http
      .post(`${this.SERVER_URL}/orders/payment`, null)
      .subscribe((res: { success: boolean }) => {
        if (res.success) {
          this.resetServerData();
          this.http
            .post(`${this.SERVER_URL}/orders/new`, {
              userId,
              products: this.cartDataClient.prodData,
            })
            .subscribe((data: OrderResponse) => {
              this.orderService
                .getOrderById(data.orderId)
                .subscribe((prods) => {
                  if (data.success) {
                    const navigationExtras: NavigationExtras = {
                      state: {
                        message: data.message,
                        products: prods,
                        orderId: data.orderId,
                        total: this.cartDataClient.total,
                      },
                    };

                    this.spinner.hide();
                    this.router.navigate(['/thankyou'], navigationExtras);
                    this.cartDataClient = {
                      total: 0,
                      prodData: [{ id: 0, incart: 0 }],
                    };
                    this.cartTotal$.next(0);
                    localStorage.setItem(
                      'cart',
                      JSON.stringify(this.cartDataClient)
                    );
                  } else {
                    this.spinner.hide();
                    this.router.navigateByUrl('/checkout');
                    this.toast.error(
                      'sorry failed to book the order',
                      'Order Status',
                      {
                        timeOut: 1500,
                        progressBar: true,
                        progressAnimation: 'decreasing',
                        positionClass: 'toast-top-right',
                      }
                    );
                  }
                });
            });
        }
      });
  }

  private resetServerData(): void {
    this.cartDataServer = {
      total: 0,
      data: [{ numInCart: 0, product: undefined }],
    };

    this.cartData$.next({ ...this.cartDataServer });
  }
}

interface OrderResponse {
  orderId: number;
  success: boolean;
  message: string;
  products: [
    {
      id: string;
      numInCart: string;
    }
  ];
}
