import {useEffect, useState} from "react";
import axios from "axios";
import {useParams, Link} from "react-router-dom";

export default function ProductDetail(){

    const { id = '' } = useParams<string>();
    const [product, setProduct] = useState({
        id: id,
        name: '',
        description: '',
        amount: 0
    });

    useEffect(() => {
        axios.get(`/api/products/${id}`)
            .then(response => {
                setProduct(response.data);
            })
            .catch(error => {
                console.log(error)
            });
    }, [id]);

    return <>
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
        </div>
    </>
}