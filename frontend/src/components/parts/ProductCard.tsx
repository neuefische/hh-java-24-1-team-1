import React from 'react';
import {Product} from '../../types/Product.ts';
import {Link} from "react-router-dom";
import './ProductCard.css';

type ProductCardProps = {
    product: Product;
};

export default function ProductCard({ product }: Readonly<ProductCardProps>): React.ReactElement {

    return (
        <div className={"productCard"}>
            <h2>Produkt: {product.name}</h2>
            <p>Artikelnummer: {product.productNumber}</p>
            <p>Menge: {product.amount}</p>
            <p><Link to={"/products/" + product.id}>Details</Link></p>
        </div>
    );
}