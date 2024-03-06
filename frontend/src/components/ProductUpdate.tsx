import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";


function ProductUpdate() {
    const { id = '' } = useParams<string>();
    const [product, setProduct] = useState({
        id: id,
        name: '',
        amount: 0
    });

    useEffect(() => {
        axios.get(`http://localhost:8000/api/products/${id}`
        ).then(response => {
            setProduct(response.data);
        })
            .catch(error => {
                console.log(error)
            });
    }, [id]);

    const navigate = useNavigate();
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        axios.put(`http://localhost:8000/api/products/update`, product)
            .then(response => {
                console.log(response)
                navigate('/')
            })
            .catch(error => {
                console.log(error)
            });
    };

    return (
        <div>
            <h2>Product Update</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name</label>
                    <input type="text" value={product.name} onChange={(e) => setProduct({...product, name: e.target.value})}/>
                </div>
                <div>
                    <label>Amount</label>
                    <input type="number" value={product.amount} onChange={(e) => setProduct({...product, amount: parseInt(e.target.value)})}/>
                </div>
                <button type="button" >Update</button>
            </form>

        </div>
    );
}

export default ProductUpdate;