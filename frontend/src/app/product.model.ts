export interface Color {
  name: string;
  image: string;
}

export interface Product {
  id: number;
  name: string;
  price: number;
  imageUrls: string[];
  description: string;
  colors: Color[];
}
