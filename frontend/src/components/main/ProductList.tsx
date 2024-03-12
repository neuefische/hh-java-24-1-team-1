import React, {useState} from 'react';
import {Product} from '../../types/Product.ts';
import SearchBar from "../utility/SearchBar.tsx";
import ProductCard from "../parts/ProductCard.tsx";
import './ProductList.css';

type ProductListProps = {
    products:Product[];
    handleSearchText: (searchText:string) => void;
    handleFilterChange: (filter:string) => void;
}

export function ProductList(props:Readonly<ProductListProps>): React.ReactElement {
    const [searchResults, setSearchResults] = useState<Product[]>();

    return (
        <>
            <SearchBar handleFilterChange={props.handleFilterChange} handleSearchText={props.handleSearchText}
                       setResult={setSearchResults} products={props.products}/>
            <main className={"productList"}>

                <table className={"productListTable"}>
                    <thead>
                        <tr>
                            <th>Produktname</th>
                            <th>Artikelnummer</th>
                            <th>Anzahl auf Lager</th>
                            <th>Details</th>
                        </tr>
                    </thead>
                    {
                        searchResults ?
                            searchResults.map((product: Product) => (
                                <ProductCard key={product.id} product={product}/>)) :
                            props.products.map((product: Product) => (
                                <ProductCard key={product.id} product={product}/>))
                    }
                </table>
            </main>
        </>

    );
}
