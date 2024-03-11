import './AddProductGallery.css'
import AddProductForm from "./AddProductForm.tsx";
import {FormEvent, useState} from "react";
import {ProductDTO} from "../types/ProductDTO.ts";
import ProductDtoCard from "./ProductDtoCard.tsx";

type AddProductGalleryProps = {
    saveProduct:(productDto:ProductDTO) => void;
}

export default function AddProductGallery(props:Readonly<AddProductGalleryProps>):JSX.Element{
    const [submittedProducts, setSubmittedProducts] = useState<ProductDTO[]>([]);

    function handleSubmit(event:FormEvent<HTMLFormElement>, newProduct:ProductDTO):void {
        event.preventDefault();
        props.saveProduct(newProduct);
        setSubmittedProducts(submittedProducts.concat(newProduct))
    }

    return (
        <main className={"addProductGallery"}>
            <AddProductForm handleSubmit={handleSubmit}/>
            <div className={"lowerDiv"}>
                {
                    submittedProducts.length > 0 && submittedProducts.map((productDto: ProductDTO) => <ProductDtoCard key={productDto.productNumber} productDto={productDto}/>)
                }
            </div>
        </main>
    )
}