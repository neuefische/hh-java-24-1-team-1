import React from 'react';
import {ProductDTO} from '../../../types/ProductDTO.ts';
import './ProductDtoCard.css';

type ProductCardProps = {
    productDto: ProductDTO;
};

export default function ProductDtoCard({ productDto }: Readonly<ProductCardProps>): React.ReactElement {

    return (
        <div className={"productDtoCard"}>
            <h2>{productDto.name}</h2>
            <p>Menge: {productDto.amount}</p>
            <p>Beschreibung: {productDto.description}</p>
            <p>Mindestbestand: {productDto.minimumStockLevel}</p>
        </div>
    );
}
