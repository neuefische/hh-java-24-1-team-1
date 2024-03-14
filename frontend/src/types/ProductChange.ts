import {Product} from "./Product.ts";

export type ProductChange = {
    products:Product[],
    description:string,
    type:string,
    status:string,
    date:string
}
