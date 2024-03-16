import {StorageSpace} from "../../../types/StorageSpace.ts";
import StorageSpaceCard from "../parts/StorageSpaceCard.tsx";
import React from "react";


type StorageSpaceGalleryProps = {
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
            <table>
                <thead>
                    <tr>
                        <th>Lagerplatznummer</th>
                        <th>Belegt?</th>
                    </tr>
                </thead>
                <tbody>
                {

                        props.storageSpaces.map((space:StorageSpace) => (<StorageSpaceCard key={space.id} space={space}/>))
                }
                </tbody>
            </table>
        </main>)
}