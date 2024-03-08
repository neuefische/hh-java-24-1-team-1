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
    const [searchText, setSearchText] = useState<string>("");

    return (
        <main>
            <SearchBar handleSearchText={setSearchText}/>
            {
                props.products.filter(product =>
                    product.name.toLowerCase().includes(searchText.toLowerCase())
                    || product.description.toLowerCase().includes(searchText.toLowerCase())
                )
                    .map((product:Product) => (
                        <ProductCard key={product.id} product={product}/>
                        )
                    )
            }
        </main>
    );
}