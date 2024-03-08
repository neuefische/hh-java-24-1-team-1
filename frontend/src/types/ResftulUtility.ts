import {ProductDTO} from "./ProductDTO.ts";
import {Product} from "./Product.ts";

export type ResftulUtility = {
    getById: () => void,
    getAll: () => void,
    post: (newProduct:ProductDTO) => Product,
    put: () => void,
    delete:() => void,
}