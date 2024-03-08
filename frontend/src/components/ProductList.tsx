import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Product} from '../types/Product.ts';
import {Link} from "react-router-dom";
import SearchBar from "./SearchBar.tsx";
import ProductCard from "./ProductCard.tsx";

export function ProductList(): React.ReactElement {
    const [products, setProducts] = useState<Product[]>([]);
    const [searchText, setSearchText] = useState<string>("");

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

    const filteredProducts = products.filter(product =>
        product.name.toLowerCase().includes(searchText.toLowerCase())
        || product.description.toLowerCase().includes(searchText.toLowerCase())
    );

    return (
        <div>
            <SearchBar handleSearchText={setSearchText}/>
            {filteredProducts.map(product => (
                <Link to={"products/" + product.id} key={product.id}>
                    <div>
                        <SearchBar handleSearchText={setSearchText}/>
                        {filteredProducts.map(product => (
                            <ProductCard key={product.productNumber} product={product}/>
                        ))}
                    </div>
                </Link>
            ))}
        </div>
    );
}