import {StorageSpace} from "../../../types/StorageSpace.ts";
import React from "react";
import {Product} from "../../../types/Product.ts";
import ProductCard from "../../product/parts/ProductCard.tsx";

type StorageSpaceCardProps = {
    space: StorageSpace;
    product: Product | null;
}

export default function StorageSpaceCard(props: Readonly<StorageSpaceCardProps>): React.ReactElement {
    return (
        <tr className={"storageSpaceCard"}>
            <td><p>{props.space.storageSpaceName}</p></td>
            <td>{props.space.isOccupied ? <p className={"yesDiv"}>"Ja"</p> : <p className={"noDiv"}>"Nein"</p>}</td>
            <td><p>{props.product ? <ProductCard product={props.product}/> : "Kein Produkt"}</p></td>
        </tr>
    );
}