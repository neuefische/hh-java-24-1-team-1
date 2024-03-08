import {ProductDTO} from "./ProductDTO.ts";
import {Product} from "./Product.ts";

export type RestfulUtility = {
    getAllProducts: () => void,
    getProductById: (id:string) => Product,
    postProduct: (newProduct:ProductDTO) => Product,
    putProduct: (updatedProduct:Product) => Product,
    deleteProductById: (id:string) => void,
}