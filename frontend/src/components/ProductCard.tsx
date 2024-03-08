import React, {useState} from 'react';
import {Product} from '../types/Product.ts';
import {NavigateFunction, useNavigate} from "react-router-dom";

type ProductCardProps = {
    product: Product;
};

export default function ProductCard({ product }: Readonly<ProductCardProps>): React.ReactElement {
    const [showDescription, setShowDescription] = useState(false);

    const navigate:NavigateFunction = useNavigate();

    const toggleDescription = () => {
        setShowDescription(!showDescription);
    };

    return (
        <div style={{ border: '1px solid #ddd', padding: '10px', borderRadius: '5px', margin: '10px 0' }}>
            <h2>{product.name}</h2>
            <p>Menge: {product.amount}</p>
            {showDescription && <p>Beschreibung: {product.description}</p>}
            <button onClick={toggleDescription}>{showDescription ? 'Weniger' : 'Mehr'}</button>
            <button onClick={() => navigate("/products/" + product.id)}>Zu den Details</button>
        </div>
    );
}
