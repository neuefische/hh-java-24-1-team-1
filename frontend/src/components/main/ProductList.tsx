import React, {useState} from 'react';
import {Product} from '../../types/Product.ts';
import SearchBar from "../utility/SearchBar.tsx";
import ProductCard from "../parts/ProductCard.tsx";

type ProductListProps = {
    products:Product[]
}

export function ProductList(props:Readonly<ProductListProps>): React.ReactElement {
    const [searchResults, setSearchResults] = useState<Product[]>();

    return (
        <main>
            <SearchBar setResult={setSearchResults} products={props.products}/>
            {
                searchResults ?
                    searchResults.map((product:Product) => (<ProductCard key={product.id} product={product}/>)):
                    props.products.map((product:Product) => (<ProductCard key={product.id} product={product}/>))
            }
        </main>
    );
}