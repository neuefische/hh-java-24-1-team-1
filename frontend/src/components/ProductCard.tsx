import React, {useState} from 'react';
import {Product} from '../types/Product.ts';

type ProductCardProps = {
    product: Product;
};

export default function ProductCard({ product }: Readonly<ProductCardProps>): React.ReactElement {
    const [showDescription, setShowDescription] = useState(false);

    const toggleDescription = () => {
        setShowDescription(!showDescription);
    };

    return (
        <div style={{ border: '1px solid #ddd', padding: '10px', borderRadius: '5px', margin: '10px 0' }}>
            <h2>{product.name}</h2>
            <p>Menge: {product.amount}</p>
            {showDescription && <p>Beschreibung: {product.description}</p>}
            <button onClick={toggleDescription}>{showDescription ? 'Weniger' : 'Details'}</button>
            <button onClick={toggleDescription}>Zu den Details</button>
        </div>
    );
}
