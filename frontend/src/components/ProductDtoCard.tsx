import React, {useState} from 'react';
import {ProductDTO} from '../types/ProductDTO.ts';

type ProductCardProps = {
    productDto: ProductDTO;
};

export default function ProductDtoCard({ productDto }: Readonly<ProductCardProps>): React.ReactElement {
    const [showDescription, setShowDescription] = useState(false);


    const toggleDescription = () => {
        setShowDescription(!showDescription);
    };

    return (
        <div style={{ border: '1px solid #ddd', padding: '10px', borderRadius: '5px', margin: '10px 0' }}>
            <h2>{productDto.name}</h2>
            <p>Menge: {productDto.amount}</p>
            {showDescription && <p>Beschreibung: {productDto.description}</p>}
            {showDescription && <p>Artikelnummer: {productDto.productNumber}</p>}
            {showDescription && <p>Mindestbestand: {productDto.minimumStockLevel}</p>}
            <button onClick={toggleDescription}>{showDescription ? 'Weniger' : 'Mehr'}</button>
        </div>
    );
}
