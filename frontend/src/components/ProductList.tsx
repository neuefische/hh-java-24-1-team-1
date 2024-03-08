import React, {useState} from 'react';
import {Product} from '../types/Product.ts';
import SearchBar from "./SearchBar.tsx";
import ProductCard from "./ProductCard.tsx";

type ProductListProps = {
    products:Product[]
}

export function ProductList(props:Readonly<ProductListProps>): React.ReactElement {
    const [searchResults, setSearchResults] = useState<Product[]>();

    return (
        <main>
            <SearchBar handleSearch={setSearchResults} products={props.products}/>
            {
                searchResults ?
                    searchResults.map((product:Product) => (<ProductCard key={product.id} product={product}/>)):
                    props.products.map((product:Product) => (<ProductCard key={product.id} product={product}/>))
            }
        </main>
    );
}