export interface Product {
    productId?: number;
    productName: string;
    productPrice: number;
    productDescription: string;
    categoryId: number;
    categoryName: string;
    productCreationDate?: Date;
}
