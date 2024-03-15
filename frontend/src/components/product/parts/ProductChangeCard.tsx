import './ProductChangeCard.css';
import {ProductChange} from "../../../types/ProductChange.ts";
import React from "react";
import ProductCard from "./ProductCard.tsx";
import {Product} from "../../../types/Product.ts";

type ChangeCardProps = {
    change:ProductChange;
}
export default function ProductChangeCard(props:Readonly<ChangeCardProps>): React.ReactElement {
    return (
        <tr className={"changeCard"}>
            <td><p>{props.change.date}</p></td>
            <td><p>{props.change.description}</p></td>
            <td><p>{props.change.type}</p></td>
            <td><p>{props.change.status}</p></td>
            {props.change.products.map((product: Product): React.ReactElement => (<ProductCard key={product.id} product={product}/>))}
        </tr>
    );
}