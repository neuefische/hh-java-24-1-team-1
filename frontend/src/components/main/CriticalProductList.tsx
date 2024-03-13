import {useState} from "react";
import {Product} from "../../types/Product.ts";
import SearchBar from "../utility/SearchBar.tsx";
import ProductCard from "../parts/ProductCard.tsx";
import './ProductList.css';

type CriticalProductListProps = {
    products: Product[];
    handleSearchText: (searchText:string) => void;
    handleFilterChange: (filter:string) => void;
}

function CriticalProductList(props:Readonly<CriticalProductListProps>):JSX.Element {
    const [searchResults, setSearchResults] = useState<Product[]>();

    return (
        <>
            <SearchBar handleFilterChange={props.handleFilterChange} handleSearchText={props.handleSearchText}
                       setResult={setSearchResults} products={props.products}/>
            <main  className={"productList"}>
                <table className={"productListTable"}>
                    <thead>
                    <tr>
                        <th>Produktname</th>
                        <th>Artikelnummer</th>
                        <th>Anzahl auf Lager</th>
                        <th>Details</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        searchResults ?
                            searchResults
                                .filter((product: Product) => product.minimumStockLevel > product.amount)
                                .map((product: Product) => (<ProductCard key={product.id} product={product}/>)) :
                            props.products
                                .filter((product: Product) => product.minimumStockLevel > product.amount)
                                .map((product: Product) => (<ProductCard key={product.id} product={product}/>))
                    }
                    </tbody>
                </table>
            </main>
        </>

);
}

export default CriticalProductList;