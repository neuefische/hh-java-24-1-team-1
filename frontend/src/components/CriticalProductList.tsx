import {useEffect, useState} from "react";
import {Product} from "../types/Product.ts";
import axios from "axios";
import SearchBar from "./SearchBar.tsx";
import {Link} from "react-router-dom";


function CriticalProductList() {
    const [products, setProducts] = useState<Product[]>([]);
    const [searchText, setSearchText] = useState<string>("");

    function fetchData() {
        axios.get('/api/products/critical')
            .then(response => {
                setProducts(response.data);
            })
            .catch(error => {
                console.error('Error fetching products:', error);
            });
    }

    useEffect(fetchData, []);

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
                        <h2>{product.name}</h2>
                        <p>Menge: {product.amount}</p>
                        <p>Mindestbestand: {product.minimumStockLevel}</p>
                    </div>
                </Link>
            ))}
        </div>
    );
}

export default CriticalProductList;