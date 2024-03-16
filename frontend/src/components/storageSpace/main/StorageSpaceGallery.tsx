import './StorageSpaceGallery.css';
import {StorageSpace} from "../../../types/StorageSpace.ts";
import StorageSpaceCard from '../parts/StorageSpaceCard.tsx';
import React from "react";
import {Product} from "../../../types/Product.ts";

type StorageSpaceGalleryProps = {
    products:Product[];
    storageSpaces: StorageSpace[];
    getEmptyStorageSpaces: () => StorageSpace[];
    getOccupiedStorageSpaces: () => StorageSpace[];
    getOccupiedStorageSpacesCount: () => number;
}

export default function StorageSpaceGallery(props:Readonly<StorageSpaceGalleryProps>):React.ReactElement{

    return (
        <main className={"storageSpaceGallery"}>
            <div>
                <h2>Lagerplätze</h2>
                <p>Belegte Lagerplätze: {props.getOccupiedStorageSpacesCount()}</p>
                <p>Freie Lagerplätze: {props.getEmptyStorageSpaces().length}</p>
            </div>
            <table className={"storageSpaceGalleryTable"}>
                <thead>
                    <tr>
                        <th>Lagerplatznummer</th>
                        <th>Belegung</th>
                        <th className={"productHeadColumn"}>
                            <table className={"productHeadTable"}>
                                <thead className={"productHeadTbody"}>
                                <tr>
                                    <th>Name</th>
                                    <th>Artikel Nr.</th>
                                    <th>Auf Lager</th>
                                    <th>Details</th>
                                </tr>
                                </thead>
                            </table>
                        </th>
                    </tr>
                </thead>
                <tbody>
                {
                    props.storageSpaces.map((space: StorageSpace) =>
                        (<StorageSpaceCard
                            key={space.id}
                            space={space}
                            product={props.products.find((product: Product) => product.storageSpaceName === space.storageSpaceName) ?? null}
                        />))
                }
                </tbody>
            </table>
        </main>)
}