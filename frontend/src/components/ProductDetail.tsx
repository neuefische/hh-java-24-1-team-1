import './ProductDetail.css'
import {useEffect, useState} from "react";
import axios from "axios";
import {NavigateFunction, useParams, Link, useNavigate} from "react-router-dom";

export default function ProductDetail(){

    const { id = '' } = useParams<string>();
    const [product, setProduct] = useState({
        id: id,
        name: '',
        description: '',
        amount: 0
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
            <p>{product.name}</p>
            <p>{product.id}</p>
            <p>{product.amount}</p>
            <p>{product.description}</p>
            {
            <Link to={`/products/${id}/edit`}>
                <button type="button">Update</button>
            </Link>
            }
            <button className={"deleteButton"} onClick={deleteProduct} type={"button"}>Löschen</button>
        </div>
    )
}