import React, {useState} from 'react';
import {Product} from '../types/Product.ts';
import {Link} from "react-router-dom";

type ProductCardProps = {
    product: Product;
};

export default function ProductCard({ product }: Readonly<ProductCardProps>): React.ReactElement {
    const [showMore, setShowMore] = useState(false);

    const toggleDescription = () => {
        setShowMore(!showMore);
    };

    return (
        <div style={{ border: '1px solid #ddd', padding: '10px', borderRadius: '5px', margin: '10px 0' }}>
            <h2>{product.name}</h2>
            <p>Menge: {product.amount}</p>
            {showMore &&
                <>
                    <p>Beschreibung: {product.description}</p>
                    <p><Link to={"/products/" + product.id}>Details</Link></p>
                </>
            }
            <button onClick={toggleDescription}>{showMore ? 'Weniger' : 'Mehr'}</button>
        </div>
    );
}
