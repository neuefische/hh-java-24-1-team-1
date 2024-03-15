import {StorageSpace} from "../../../types/StorageSpace.ts";
import {useState} from "react";


type StorageSpaceGalleryProps = {
    storageSpaces: StorageSpace[];
    getEmptyStorageSpaces: () => StorageSpace[];
    getOccupiedStorageSpaces: () => StorageSpace[];
    getOccupiedStorageSpacesCount: () => number;
}
export default function StorageSpaceGallery(props:Readonly<StorageSpaceGalleryProps>){
    const [storageSpaces, setStorageSpaces] = useState<StorageSpace[]>(props.storageSpaces);

    function showEmptyStorageSpaces(event:MouseEvent){
        event.preventDefault();
        setStorageSpaces(props.getEmptyStorageSpaces());
    }

    function showOccupiedStorageSpaces(event:MouseEvent){
        setStorageSpaces(props.getOccupiedStorageSpaces());
    }

    return (
        <main>
            <table>
                <thead>
                    <tr>
                        <
                    </tr>
                </thead>
            </table>
        </main>)
}