import './AddProductGallery.css'
import AddProductCard from "./AddProductCard.tsx";
import {FormEvent, useState} from "react";
import ProductCard from "./ProductCard.tsx";
import {Product} from "../types/Product.ts";
import {ProductDTO} from "../types/ProductDTO.ts";
import {RestfulUtility} from "../types/RestfulUtility.ts";

type AddProductGalleryProps = {
    restfulUtility:RestfulUtility,
}

export default function AddProductGallery(props:Readonly<AddProductGalleryProps>):JSX.Element{
    const [submittedProducts, setSubmittedProducts] = useState<Product[]>([]);

    function handleSubmit(event:FormEvent<HTMLFormElement>, newProduct:ProductDTO):void {
        event.preventDefault();
        setSubmittedProducts(submittedProducts.concat(props.restfulUtility.postProduct(newProduct)))
    }

    return (
        <main className={"addProductGallery"}>
            <AddProductCard handleSubmit={handleSubmit}/>
            { submittedProducts.length > 0 && submittedProducts.map((product:Product)=> <ProductCard key={product.id} product={product}/>) }
        </main>
    )
}