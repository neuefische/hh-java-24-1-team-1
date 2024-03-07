import 'AddProductGallery.css'
import AddProductCard from "./AddProductCard.tsx";
import {FormEvent, useState} from "react";
import ProductCard from "./ProductCard.tsx";
import {Product} from "../types/Product.ts";
import axios from "axios";
import {ProductDTO} from "../types/ProductDTO.ts";

export default function AddProductGallery():JSX.Element{
    const [submittedProducts, setSubmittedProducts] = useState<Product[]>([]);

    function handleSubmit(event:FormEvent<HTMLFormElement>, newProduct:ProductDTO):void {
        event.preventDefault();
        axios.post('../api/products', newProduct)
            .then(response => {
                setSubmittedProducts(submittedProducts.concat(response.data));
                console.log("New product added with id " + response.data.id + ".");
            })
            .catch(error => {
                console.error("Error creating product: ", error.message);
            })
    }

    return (
        <main>
            <AddProductCard handleSubmit={handleSubmit}/>
            {
                submittedProducts ?
                    submittedProducts.map((product:Product)=> <ProductCard key={product.id} product={product}/>) :
                    <></>
            }
        </main>
    )
}