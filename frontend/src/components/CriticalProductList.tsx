import {useState} from "react";
import {Product} from "../types/Product.ts";
import SearchBar from "./SearchBar.tsx";
import ProductCard from "./ProductCard.tsx";

type CriticalProductListProps = {
    products: Product[];
}

function CriticalProductList(props:Readonly<CriticalProductListProps>):JSX.Element {
    const [searchResults, setSearchResults] = useState<Product[]>();

    return (
        <main className={"criticalProductList"}>
            <SearchBar handleSearch={setSearchResults} products={props.products}/>
            {
                searchResults ?
                    searchResults
                        .filter((product:Product) => product.minimumStockLevel > product.amount)
                        .map((product: Product) => (<ProductCard key={product.id} product={product}/>)) :
                    props.products
                        .filter((product:Product) => product.minimumStockLevel > product.amount)
                        .map((product: Product) => (<ProductCard key={product.id} product={product}/>))
            }
        </main>
    );
}

export default CriticalProductList;