import './AddProductGallery.css'
import AddProductCard from "./AddProductCard.tsx";
import {FormEvent, useState} from "react";
import {ProductDTO} from "../types/ProductDTO.ts";
import {RestfulUtility} from "../types/RestfulUtility.ts";
import ProductDtoCard from "./ProductDtoCard.tsx";

type AddProductGalleryProps = {
    restfulUtility:RestfulUtility,
}

export default function AddProductGallery(props:Readonly<AddProductGalleryProps>):JSX.Element{
    const [submittedProducts, setSubmittedProducts] = useState<ProductDTO[]>([]);

    function handleSubmit(event:FormEvent<HTMLFormElement>, newProduct:ProductDTO):void {
        event.preventDefault();
        props.restfulUtility.postProduct(newProduct);
        setSubmittedProducts(submittedProducts.concat(newProduct))
    }

    return (
        <main className={"addProductGallery"}>
            <AddProductCard handleSubmit={handleSubmit}/>
            { submittedProducts.length >0 && submittedProducts.map((productDto:ProductDTO)=> <ProductDtoCard key={productDto.productNumber} productDto={productDto}/>) }
        </main>
    )
}