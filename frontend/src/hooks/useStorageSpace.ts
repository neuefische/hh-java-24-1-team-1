import {StorageSpace} from '../types/StorageSpace';
import {useEffect, useState} from "react";
import axios from "axios";

export default function useStorageSpace() {
    const [storageSpaces, setStorageSpaces] = useState<StorageSpace[]>([]);

    function fetchStorageSpace():void {
        axios.get('/api/storage')
            .then(response => {
                setStorageSpaces(response.data);

            })
            .catch(error => {
                console.error('Es gab ein Problem beim Abrufen der Lagerplätze:', error.message);
            });
    }

    function getEmptyStorageSpaces():StorageSpace[] {
        const emptySpaces:StorageSpace[] = storageSpaces.filter((space:StorageSpace) => !space.isOccupied);

        if(emptySpaces.length === 0) return [];
        else return emptySpaces;
    }

    function getOccupiedStorageSpaces():StorageSpace[] {
        const occupiedSpaces:StorageSpace[] = storageSpaces.filter((space:StorageSpace) => space.isOccupied);

        if(occupiedSpaces.length === 0) return [];
        else return occupiedSpaces;
    }

    function getOccupiedStorageSpacesCount():number {
        return getOccupiedStorageSpaces().length;
    }

    useEffect(()=> fetchStorageSpace(), []);

    return {
        storageSpaces,
        getEmptyStorageSpaces,
        getOccupiedStorageSpaces,
        getOccupiedStorageSpacesCount,

    }
}
