import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Product} from '../types/Product.ts';
import {Link} from "react-router-dom";
import SearchBar from "./SearchBar.tsx";
import {RestfulUtility} from "../types/RestfulUtility.ts";
import {Product} from "../types/Product.ts";

type ProductListProps = {
    restfulUtility:RestfulUtility,
    products:Product[]
}

export function ProductList(props:Readonly<ProductListProps>): React.ReactElement {
    const [products, setProducts] = useState<Product[]>([]);
    const [searchText, setSearchText] = useState<string>("");
    useEffect(fetchProducts, []);

    const filteredProducts = products.filter(product =>
        product.name.toLowerCase().includes(searchText.toLowerCase())
        || product.description.toLowerCase().includes(searchText.toLowerCase())
    );
    return (
        <main>
            {props.products.map((product:Product) => (
                <Link to={"products/" + product.id}>
                    <div key={product.id}>
            <SearchBar handleSearchText={setSearchText}/>
            {filteredProducts.map(product => (
                <Link to={"products/" + product.id} key={product.id}>
                    <div>
                        <h2>{product.name}</h2>
                        <p>Menge: {product.amount}</p>
                        <p>Beschreibung: {product.description}</p>
                        <p>Artikelnummer: {product.productNumber}</p>
                    </div>
                </Link>
            ))}
        </main>
    );
}