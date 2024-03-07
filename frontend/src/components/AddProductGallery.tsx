import 'AddProductGallery.css'
import AddProductCard from "./AddProductCard.tsx";
import {useState} from "react";
import ProductCard from "./ProductCard.tsx";
import {Product} from "../types/Product.ts";

export default function AddProductGallery():JSX.Element{
    const [submittedProducts, setSubmittedProducts] = new useState<Product[]>([])

    function handleSubmit(event){
    }

    return (
        <main>
            <AddProductCard handleSubmit={}/>
            {
                submittedProducts ?
                    submittedProducts.map((product:Product)=> <ProductCard key={product.id} product={product}/>) :
                    <></>
            }
        </main>
    )
}