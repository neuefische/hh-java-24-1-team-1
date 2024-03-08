import {ProductDTO} from "./ProductDTO.ts";
import {Product} from "./Product.ts";

export type RestfulUtility = {
    getAllProducts: () => void,
    getProductById: (id:string) => Product,
    postProduct: (newProduct:ProductDTO) => void,
    putProduct: (updatedProduct:Product) => void,
    deleteProductById: (id:string) => void,
}