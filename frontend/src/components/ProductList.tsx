import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Product} from '../types/Product.ts';
import {Link} from "react-router-dom";

export function ProductList(): React.ReactElement {
    const [products, setProducts] = useState<Product[]>([]);

    function fetchProducts() {
        axios.get('/api/products')
            .then(response => {
                setProducts(response.data);
            })
            .catch(error => {
                console.error('Es gab ein Problem beim Abrufen der Produkte:', error);
            });
    }


    useEffect(fetchProducts, []);


    return (
        <div>
            {products.map(product => (
                <Link to={"products/" + product.id}>
                    <div key={product.id}>
                        <h2>{product.name}</h2>
                        <p>Menge: {product.amount}</p>
                        <p>Beschreibung: {product.description}</p>
                    </div>
                </Link>

            ))}
        </div>
    );
}