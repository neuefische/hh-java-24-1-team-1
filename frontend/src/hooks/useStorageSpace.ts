import {StorageSpace} from '../types/StorageSpace';
import {useEffect, useState} from "react";
import axios from "axios";

export default function useStorageSpace() {
    const [storageSpace, setStorageSpace] = useState<StorageSpace[]>([]);

    function fetchStorageSpace():void {
        axios.get('/api/storage')
            .then(response => {
                setStorageSpace(response.data);

            })
            .catch(error => {
                console.error('Es gab ein Problem beim Abrufen der Lagerplätze:', error.message);
            });
    }

    function getEmptyStorageSpaces():StorageSpace[] {
        const emptySpaces:StorageSpace[] = storageSpace.filter((space:StorageSpace) => !space.isOccupied);

        if(emptySpaces.length === 0) console.error('Kein freier Lagerplatz gefunden.');
        else return emptySpaces;
        return [{storageSpaceId:'',isOccupied:false}];
    }

    function getOccupiedStorageSpaces():StorageSpace[] {
        const occupiedSpaces:StorageSpace[] = storageSpace.filter((space:StorageSpace) => space.isOccupied);

        if(occupiedSpaces.length === 0) console.error('Kein belegter Lagerplatz gefunden.');
        else return occupiedSpaces;
        return [{storageSpaceId:'',isOccupied:false}];
    }

    function getOccupiedStorageSpacesCount():number {
        return getOccupiedStorageSpaces().length;
    }

    function postNewStorageSpace(newStorageSpace:StorageSpace) {
        axios.post('/api/storage', newStorageSpace)
            .then((response) => {
                console.log("New storage space added with id " + response.data.id + ".");
                fetchStorageSpace();
            })
            .catch(error => {
                console.error("Error creating storage space: ", error.message);
            })
    }

    useEffect(()=> fetchStorageSpace(), []);


    return {
        storageSpace,
        getEmptyStorageSpaces,
        getOccupiedStorageSpaces,
        getOccupiedStorageSpacesCount,
        postNewStorageSpace
    }
}
