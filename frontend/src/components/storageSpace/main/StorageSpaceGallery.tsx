import {StorageSpace} from "../../../types/StorageSpace.ts";
import {useEffect, useState} from "react";
import StorageSpaceCard from "../parts/StorageSpaceCard.tsx";


type StorageSpaceGalleryProps = {
    storageSpaces: StorageSpace[];
    getEmptyStorageSpaces: () => StorageSpace[];
    getOccupiedStorageSpaces: () => StorageSpace[];
    getOccupiedStorageSpacesCount: () => number;
}
export default function StorageSpaceGallery(props:Readonly<StorageSpaceGalleryProps>){
    const [storageSpaces, setStorageSpaces] = useState<StorageSpace[]>(props.storageSpaces);

    useEffect(() => {
        setStorageSpaces(props.storageSpaces);
    }, []);

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
                    storageSpaces ?
                        storageSpaces.map((space:StorageSpace) => (<StorageSpaceCard key={space.storageSpaceName} space={space}/>)) :
                        props.storageSpaces.map((space:StorageSpace) => (<StorageSpaceCard key={space.storageSpaceName} space={space}/>))
                }
                </tbody>
            </table>
        </main>)
}