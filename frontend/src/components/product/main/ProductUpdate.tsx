import * as yup from 'Yup';
import {ProductDataSchema, FormError} from "../../../types/ProductDataSchema.ts";

import './ProductUpdate.css'
import {useNavigate, useParams} from "react-router-dom";
import {FormEvent, useState} from "react";
import {Product} from "../../../types/Product.ts";

type ProductUpdateProps = {
    updateProduct: (product:Product) => void,
    getProductById:(id:string) => Product,
    availableStorageSpaces: string[]
}

function ProductUpdate(props:Readonly<ProductUpdateProps>) {
    const { id = '' } = useParams<string>();
    const storageSpaceName = props.getProductById(id).storageSpaceName;
    const [product, setProduct] = useState<Product>(props.getProductById(id));
    const [formError, setFormError] = useState<FormError>({});

    const navigate = useNavigate();

    function handleSubmit(e: FormEvent):void {
        e.preventDefault();
        ProductDataSchema.validate(product, {abortEarly: false})
            .then(() => {
                props.updateProduct(product)
                navigate("/");
            }).catch((validationErrors: yup.ValidationError) => {
            // Validation failed
            const errors = validationErrors.inner.reduce<{ [key: string]: string }>((acc, currentError) => {
                // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                // @ts-expect-error
                acc[currentError.path] = currentError.message;
                return acc;
            }, {});
            setFormError(errors);
        });
    }

    return (
        <main className={"productUpdate"}>
            <h2>Product Update</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor={"name"}>Name</label>
                    <input type="text" value={product.name} name={"name"}
                           onChange={(e) => setProduct({...product, name: e.target.value})}/>
                    {formError.name && <p className={"error"}>{formError.name}</p>}
                </div>
                <div>
                    <label htmlFor={"storageSpace"}>Lagerplatz</label>
                    <select value={product.storageSpaceName} name={"storageSpace"}
                            onChange={(e) => setProduct({...product, storageSpaceName: e.target.value})}>
                        <option key={storageSpaceName} value={storageSpaceName}>{storageSpaceName}</option>
                        {props.availableStorageSpaces.map((storageSpaceName:string) => {
                            return <option key={storageSpaceName} value={storageSpaceName}>{storageSpaceName}</option>
                        })}
                    </select>
                </div>
                <div>
                    <label htmlFor={"description"}>Description</label>
                    <input type="text" value={product.description} name={"description"}
                           onChange={(e) => setProduct({...product, description: e.target.value})}/>
                    {formError.description && <p className={"error"}>{formError.description}</p>}
                </div>
                <div>
                    <label htmlFor={"amount"}>Amount</label>
                    <input type="number" value={product.amount} name={"amount"}
                           onChange={(e) => setProduct({...product, amount: parseInt(e.target.value)})}/>
                    {formError.amount && <p className={"error"}>{formError.amount}</p>}
                </div>
                <div>
                    <label htmlFor={"productNumber"}>Product number</label>
                    <input type="text" value={product.productNumber} name={"productNumber"}
                           onChange={(e) => setProduct({...product, productNumber: e.target.value})}/>
                    {formError.productNumber && <p className={"error"}>{formError.productNumber}</p>}
                </div>
                <div>
                    <label htmlFor={"minimumStockLevel"}>Minimum stock level</label>
                    <input type="number" value={product.minimumStockLevel} name={"minimumStockLevel"}
                           onChange={(e) => setProduct({...product, minimumStockLevel: parseInt(e.target.value)})}/>
                    {formError.minimumStockLevel && <p className={"error"}>{formError.minimumStockLevel}</p>}
                </div>
                <div>
                    <button type="submit">Update</button>
                </div>
            </form>
        </main>
    );
}

export default ProductUpdate;