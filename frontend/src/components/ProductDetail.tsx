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
        <main>
            <h2>Product Detail</h2>
            <p>{product.name}</p>
            <p>{product.id}</p>
            <p>{product.amount}</p>
            <p>{product.description}</p>
            <p>{product.productNumber}</p>
            <p>{product.minimumStockLevel}</p>
            <button type="button" onClick={() => navigate(`/products/${id}/edit`)}>Update</button>
            <button className={"deleteButton"} onClick={handleDeleteProduct} type={"button"}>Löschen</button>
        </main>
    )
}