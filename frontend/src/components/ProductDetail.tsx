import './ProductDetail.css'
import {useEffect, useState} from "react";
import {NavigateFunction, useNavigate, useParams} from "react-router-dom";
import {Product} from "../types/Product.ts";

type ProductDetailProps = {
    getProductById:(id:string)=>Product,
    deleteProduct:(id:string)=>void
}

export default function ProductDetail(props:Readonly<ProductDetailProps>):JSX.Element{
    const { id = '' } = useParams<string>();
    const [product, setProduct] = useState<Product>({
        id: "",
        name: "",
        description: "",
        amount: 0,
        productNumber: "",
        minimumStockLevel: 0
    });

    const navigate:NavigateFunction = useNavigate();

    function handleDeleteProduct():void {
        props.deleteProduct(id);
        navigate("/");
    }

    useEffect(() => setProduct(props.getProductById(id)), []);

    return (
        <main className={"productDetail"}>
            <h2>Produktdetails</h2>
            <div>
                <span>Produktname:</span>
                <span>{product.name}</span>
            </div>
            <div>
                <span>Artikelnummer:</span>
                <span>{product.productNumber}</span>
            </div>
            <div>
                <span>Anzahl auf Lager:</span>
                <span>{product.amount}</span>
            </div>
            <div>
                <span>Mindestbestand:</span>
                <span>{product.minimumStockLevel}</span>
            </div>
            <div>
                <span>Produktbeschreibung:</span>
                <span>{product.description}</span>
            </div>
            <div>
                <button type="button" onClick={() => navigate(`/products/${id}/edit`)}>Update</button>
                <button className={"deleteButton"} onClick={handleDeleteProduct} type={"button"}>Löschen</button>
            </div>
        </main>
    )
}