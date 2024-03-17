import './ProductChangeCard.css';
import {ProductChange} from "../../../types/ProductChange.ts";
import React from "react";
import {Product} from "../../../types/Product.ts";
import {Link} from "react-router-dom";

type ChangeCardProps = {
    change:ProductChange;
}
export default function ProductChangeCard(props:Readonly<ChangeCardProps>): React.ReactElement {
    return (
        <tr className={"productChangeCard"}>
            <td><p>{props.change.date}</p></td>
            <td><p>{props.change.description}</p></td>
            <td><p>{props.change.type}</p></td>
            <td><p>{props.change.status}</p></td>
            <td className={"productChangeCardProductColumn"}>
                <table className={"productChangeCardProductTable"}>
                    <tbody className={"productChangeCardProductTableBody"}>
                    {props.change.products.map((product: Product, index:number): React.ReactElement => (
                        <tr className={"productCard"} key={index}>
                            <td><p><b>{product.name}</b></p></td>
                            <td><p>{product.productNumber}</p></td>
                            <td><p>{product.amount}</p></td>
                            <td><p>{product.storageSpaceName}</p></td>
                            <td><p><Link to={"/products/" + product.id}>Details</Link></p></td>
                        </tr>
                        ))}
                    </tbody>
                </table>
            </td>
        </tr>
    );
}
