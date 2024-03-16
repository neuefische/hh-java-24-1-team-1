import './StorageSpaceCard.css';
import {StorageSpace} from "../../../types/StorageSpace.ts";
import React from "react";
import {Product} from "../../../types/Product.ts";
import {Link} from "react-router-dom";

type StorageSpaceCardProps = {
    space: StorageSpace;
    product: Product | null;
}

export default function StorageSpaceCard(props: Readonly<StorageSpaceCardProps>): React.ReactElement {
    return (
        <tr className={"storageSpaceCard"}>
            <td><p>{props.space.storageSpaceName}</p></td>
            {props.space.isOccupied ?
                <td className={"yesDiv"}><p>Belegt</p> </td>:
                <td className={"noDiv"}><p>Frei</p></td>
            }
            <td className={"productColumn"}>
                {props.product ?
                    <table className={"productTable"}>
                        <tbody className={"productTbody"}>
                        <tr>
                            <td><p><b>{props.product.name}</b></p></td>
                            <td><p>{props.product.productNumber}</p></td>
                            <td><p>{props.product.amount}</p></td>
                            <td><p><Link to={"/products/" + props.product.id}>Details</Link></p></td>
                        </tr>
                        </tbody>
                    </table> :
                    <table className={"productTable"}>
                        <tbody>
                        <tr>
                            <td><p>"Kein Produkt"</p></td>
                        </tr>
                        </tbody>
                    </table>
                }
            </td>
        </tr>
    );
}