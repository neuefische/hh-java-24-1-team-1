import './ProductDetail.css'
import {useState} from "react";
import {NavigateFunction, useParams, useNavigate} from "react-router-dom";
import {RestfulUtility} from "../types/RestfulUtility.ts";
import {Product} from "../types/Product.ts";

type ProductDetailProps = {
    restfulUtility:RestfulUtility,
}

export default function ProductDetail(props:Readonly<ProductDetailProps>){
    const { id = '' } = useParams<string>();
    const [product] = useState<Product>(props.restfulUtility.getProductById(id));

    const navigate:NavigateFunction = useNavigate();

    function handleDeleteProduct():void {
        props.restfulUtility.deleteProductById(id);
        navigate("/");
    }

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