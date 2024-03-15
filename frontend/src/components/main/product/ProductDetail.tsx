import './ProductDetail.css'
import {useEffect, useState} from "react";
import {NavigateFunction, useNavigate, useParams} from "react-router-dom";
import {Product} from "../../../types/Product.ts";
import Barcode from "../../parts/Barcode.tsx";

type ProductDetailProps = {
    getProductById:(id:string)=>Product,
    deleteProduct:(id:string)=>void
}

export default function ProductDetail(props:Readonly<ProductDetailProps>){
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
        if (window.confirm("Möchten Sie dieses Produkt wirklich löschen?")) {
            props.deleteProduct(id);
            navigate("/");
        }
    }

    useEffect(() => setProduct(props.getProductById(id)), [id, props]);

    return (
        <main className={"productDetail"}>

            <table>
                <tbody>
                    <tr>
                        <th><h2>Produktname</h2></th>
                        <td><h2>{product.name}</h2></td>
                    </tr>

                    <tr>
                        <th>Artikelnummer:</th>
                        <td>{product.productNumber}</td>
                    </tr>
                    <tr>
                        <th>Barcode:</th>
                        <td><Barcode barcode={product.productNumber}/></td>
                    </tr>
                    <tr>
                        <th>Anzahl auf Lager:</th>
                        <td>{product.amount}</td>
                    </tr>
                    <tr>
                        <th>Mindestbestand:</th>
                        <td>{product.minimumStockLevel}</td>
                    </tr>
                    <tr>
                        <th>Produktbeschreibung:</th>
                        <td>{product.description}</td>
                    </tr>
                </tbody>
            </table>
            <p>
                <button type="button" onClick={() => navigate(`/products/${id}/edit`)}>Update</button>
                <button className={"deleteButton"} onClick={handleDeleteProduct} type={"button"}>Löschen</button>
            </p>
        </main>
    )
}