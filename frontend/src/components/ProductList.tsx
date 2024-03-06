import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Product} from '../types/Product.ts';

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
                <div key={product.id}>
                    <h2>{product.name}</h2>
                    <p>Menge: {product.amount}</p>
                </div>
            ))}
        </div>
    );
}