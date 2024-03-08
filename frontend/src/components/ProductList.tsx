import React, {useState} from 'react';
import {Product} from '../types/Product.ts';
import SearchBar from "./SearchBar.tsx";
import {RestfulUtility} from "../types/RestfulUtility.ts";
import ProductCard from "./ProductCard.tsx";

type ProductListProps = {
    restfulUtility:RestfulUtility,
    products:Product[]
}

export function ProductList(props:Readonly<ProductListProps>): React.ReactElement {
    const [products] = useState<Product[]>(props.products);
    const [searchText, setSearchText] = useState<string>("");


    const filteredProducts = products.filter(product =>
        product.name.toLowerCase().includes(searchText.toLowerCase())
        || product.description.toLowerCase().includes(searchText.toLowerCase())
    );
    return (
        <main>
            <SearchBar handleSearchText={setSearchText}/>
            {filteredProducts.map(product => (
                    <ProductCard key={product.productNumber} product={product}/>
            ))}
        </main>
    );
}