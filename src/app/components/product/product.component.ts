import { ProductModelServer } from './../../models/product.model';
import { CartService } from './../../services/cart.service';
import { ProductService } from './../../services/product.service';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { map } from 'rxjs/operators';

declare let $: any;

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss'],
})
export class ProductComponent implements OnInit, AfterViewInit {
  id: number;
  product: ProductModelServer;
  thumbImages: any[] = [];

  @ViewChild('quantity') quantityInput;

  constructor(
    private readonly productService: ProductService,
    private readonly cartService: CartService,
    private readonly route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        map((param: ParamMap) => {
          return param.get('id');
        })
      )
      .subscribe((prodId) => {
        this.id = Number(prodId);
        this.productService.getProductById(this.id).subscribe((prod) => {
          this.product = prod;
          if (prod.images !== null) {
            this.thumbImages = prod.images.split(';');
          }
        });
      });
  }

  ngAfterViewInit(): void {
    // Product Main img Slick
    $('#product-main-img').slick({
      infinite: true,
      speed: 300,
      dots: false,
      arrows: true,
      fade: true,
      asNavFor: '#product-imgs',
    });

    // Product imgs Slick
    $('#product-imgs').slick({
      slidesToShow: 3,
      slidesToScroll: 1,
      arrows: true,
      centerMode: true,
      focusOnSelect: true,
      centerPadding: 0,
      vertical: true,
      asNavFor: '#product-main-img',
      responsive: [
        {
          breakpoint: 991,
          settings: {
            vertical: false,
            arrows: false,
            dots: true,
          },
        },
      ],
    });

    // Product img zoom
    const zoomMainProduct = document.getElementById('product-main-img');
    if (zoomMainProduct) {
      $('#product-main-img .product-preview').zoom();
    }
  }

  addToCart(id: number): void {
    this.cartService.addProductToCart(
      id,
      this.quantityInput.nativeElement.value
    );
  }

  increase(): void {
    let value = parseInt(this.quantityInput.nativeElement.value);
    if (this.product.quantity >= 1) {
      value++;

      if (value > this.product.quantity) {
        // @ts-ignore
        value = this.product.quantity;
      }
    } else {
      return;
    }

    this.quantityInput.nativeElement.value = value.toString();
  }

  decrease(): void {
    let value = parseInt(this.quantityInput.nativeElement.value);
    if (this.product.quantity > 0) {
      value--;

      if (value <= 0) {
        // @ts-ignore
        value = 1;
      }
    } else {
      return;
    }
    this.quantityInput.nativeElement.value = value.toString();
  }
}
