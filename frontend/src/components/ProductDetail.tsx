import './ProductDetail.css'
import {useEffect, useState} from "react";
import axios from "axios";
import {Link, NavigateFunction, useNavigate, useParams} from "react-router-dom";

export default function ProductDetail(){

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

    useEffect(() => {
        axios.get(`/api/products/${id}`)
            .then(response => {
                setProduct(response.data);
            })
            .catch(error => {
                console.log(error)
            });
    }, [id]);

    function deleteProduct():void{
        axios.delete(`/api/products/${id}`)
            .catch(error => {
                console.log(error)
            });
        navigate("/");
    }

    return (
        <div>
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
            <button className={"deleteButton"} onClick={deleteProduct} type={"button"}>Löschen</button>
        </div>
    )
}