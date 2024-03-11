import './ProductUpdate.css'
import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";
import {RestfulUtility} from "../types/RestfulUtility.ts";
import {Product} from "../types/Product.ts";

type ProductUpdateProps = {
    restfulUtility:RestfulUtility,
}

function ProductUpdate(props:Readonly<ProductUpdateProps>) {
    const { id = '' } = useParams<string>();
    const [product, setProduct] = useState<Product>(props.restfulUtility.getProductById(id));

    const navigate = useNavigate();

    function handleSubmit(e: React.FormEvent) {
        e.preventDefault();
        props.restfulUtility.putProduct(product)
        navigate("/");
    }

    return (
        <main className={"productUpdate"}>
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
                <div>
                    <button type="submit">Update</button>
                </div>
            </form>
        </main>
    );
}

export default ProductUpdate;