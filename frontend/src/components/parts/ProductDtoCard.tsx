import React, {useState} from 'react';
import {ProductDTO} from '../../types/ProductDTO.ts';
import './ProductDtoCard.css';

type ProductCardProps = {
    productDto: ProductDTO;
};

export default function ProductDtoCard({ productDto }: Readonly<ProductCardProps>): React.ReactElement {
    const [showDescription, setShowDescription] = useState(false);


    const toggleDescription = () => {
        setShowDescription(!showDescription);
    };

    return (
        <div className={"productDtoCard"}>
            <h2>{productDto.name}</h2>
            <p>Menge: {productDto.amount}</p>
            {showDescription &&
                <>
                    <p>Beschreibung: {productDto.description}</p>
                    <p>Artikelnummer: {productDto.productNumber}</p>
                    <p>Mindestbestand: {productDto.minimumStockLevel}</p>
                </>
            }
            <button onClick={toggleDescription}>{showDescription ? 'Weniger' : 'Mehr'}</button>
        </div>
    );
}
