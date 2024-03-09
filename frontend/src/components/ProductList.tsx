import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Product} from '../types/Product.ts';
import SearchBar from "./SearchBar.tsx";
import ProductCard from "./ProductCard.tsx";
import './ProductList.css';
import {useLocation} from 'react-router-dom';

export function ProductList(): React.ReactElement {
    const [products, setProducts] = useState<Product[]>([]);
    const [searchText, setSearchText] = useState<string>("");
    const { pathname } = useLocation();
    const apiUrl = pathname === '/critical' ? '/api/products/critical' : '/api/products';

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axios.get(apiUrl);
                setProducts(response.data);
            } catch (error) {
                console.error('Es gab ein Problem beim Abrufen der Produkte:', error);
            }
        };

        fetchProducts();
    }, [apiUrl]);

    const filteredProducts = products.filter(product =>
        product.name.toLowerCase().includes(searchText.toLowerCase())
        || product.description.toLowerCase().includes(searchText.toLowerCase())
    );

    return (
        <div style={{margin: '20px'}}>
            <SearchBar handleSearchText={setSearchText}/>
            <div className={"list"}>
                {filteredProducts.map(product => (
                    <ProductCard key={product.productNumber} product={product}/>
                ))}
            </div>
        </div>
    );
}