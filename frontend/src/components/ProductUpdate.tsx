import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import './ProductUpdate.css';


function ProductUpdate() {
    const { id = '' } = useParams<string>();
    const [product, setProduct] = useState({
        id: id,
        name: '',
        description: '',
        amount: 0,
        productNumber: '',
        minimumStockLevel: 0
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

    const navigate = useNavigate();
    function handleSubmit(e: React.FormEvent) {
        e.preventDefault();

        navigate(`/products/${id}`)

    };

    return (
        <div className={"productUpdate"}>
            <h2>Product Update</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor={"name"}>Name</label>
                    <input type="text" value={product.name} name={"name"}
                           onChange={(e) => setProduct({...product, name: e.target.value})}/>
                </div>
                <div>
                    <label htmlFor={"description"}>Description</label>
                    <input type="text" value={product.description} name={"description"}
                           onChange={(e) => setProduct({...product, description: e.target.value})}/>
                </div>
                <div>
                    <label htmlFor={"amount"}>Amount</label>
                    <input type="number" value={product.amount} name={"amount"}
                           onChange={(e) => setProduct({...product, amount: parseInt(e.target.value)})}/>
                </div>
                <div>
                    <label htmlFor={"productNumber"}>Product number</label>
                    <input type="text" value={product.productNumber} name={"productNumber"}
                           onChange={(e) => setProduct({...product, productNumber: e.target.value})}/>
                </div>
                <div>
                    <label htmlFor={"minimumStockLevel"}>Minimum stock level</label>
                    <input type="number" value={product.minimumStockLevel} name={"minimumStockLevel"}
                           onChange={(e) => setProduct({...product, minimumStockLevel: parseInt(e.target.value)})}/>
                </div>
                <button type="submit">Update</button>
            </form>

        </div>
    );
}

export default ProductUpdate;