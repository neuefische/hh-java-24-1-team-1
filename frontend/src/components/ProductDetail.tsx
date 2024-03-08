import './ProductDetail.css'
import {useEffect, useState} from "react";
import {NavigateFunction, useParams, Link, useNavigate} from "react-router-dom";
import {RestfulUtility} from "../types/RestfulUtility.ts";
import axios from "axios";

type ProductDetailProps = {
    restfulUtility:RestfulUtility,
}

export default function ProductDetail(props:Readonly<ProductDetailProps>){
    const { id = '' } = useParams<string>();
    const [product, setProduct] = useState({
        id: id,
        name: '',
        description: '',
        amount: 0,
        productNumber: '',
        minimumStockLevel: 0
    });

    const navigate:NavigateFunction = useNavigate();

    useEffect(() => setProduct(props.restfulUtility.getProductById(id)), []);

    function handleDeleteProduct(event: MouseEvent):void {
        event.preventDefault();
        props.restfulUtility.deleteProductById(id);
        navigate("/");
    }

    return (
        <div className={"details"}>
            <h2>Product Detail</h2>
            <p>Artikel: {product.name}</p>
            <p>Menge: {product.amount}</p>
            <p>Beschreibung: {product.description}</p>
            <p>Artikelnummer: {product.productNumber}</p>
            <p>Mindestbestand: {product.minimumStockLevel}</p>
            {
                <Link to={`/products/${id}/edit`}>
                    <button type="button">Update</button>
                </Link>
            }
            <button className={"deleteButton"} onClick={handleDeleteProduct} type={"button"}>Löschen</button>
        </div>
    )
}