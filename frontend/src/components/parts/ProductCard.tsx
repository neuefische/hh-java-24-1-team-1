import React from 'react';
import {Product} from '../../types/Product.ts';
import {Link} from "react-router-dom";
import './ProductCard.css';

type ProductCardProps = {
    product: Product;
};

export default function ProductCard({ product }: Readonly<ProductCardProps>): React.ReactElement {
    return (
        <tr className={"productCard"}>
            <td><p><b>{product.name}</b></p></td>
            <td><p>{product.productNumber}</p></td>
            <td><p>{product.amount}</p></td>
            <td><p><Link to={"/products/" + product.id}>Details</Link></p></td>
        </tr>
    );
}