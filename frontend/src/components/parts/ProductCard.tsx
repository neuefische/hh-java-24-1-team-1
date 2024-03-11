import React, {useState} from 'react';
import {Product} from '../../types/Product.ts';
import {Link} from "react-router-dom";
import './ProductCard.css';

type ProductCardProps = {
    product: Product;
};

export default function ProductCard({ product }: Readonly<ProductCardProps>): React.ReactElement {
    const [showMore, setShowMore] = useState(false);

    const toggleDescription = () => {
        setShowMore(!showMore);
    };

    return (

        <div className={"productCard"}>
            <h2>Produkt: {product.name}</h2>
            <p>Artikelnummer: {product.productNumber}</p>
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
