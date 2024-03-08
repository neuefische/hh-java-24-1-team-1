import {Product} from "../types/Product.ts";


type ProductCardProps = {
    product:Product
}
export default function ProductCard(props:Readonly<ProductCardProps>):JSX.Element{
    return (
        <h2> I am a ProductCard of the product {props.product.name} :) </h2>
    )
}