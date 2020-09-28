export interface ProductModelServer {
  id: number;

  name: string;

  quantity: number;

  description: string;

  price: number;

  image: string;

  images: string;

  category: string;
}

export interface ProductResponse {
  count: number;
  products: ProductModelServer[];
}
