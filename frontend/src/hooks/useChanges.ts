import {Change} from "../types/Change.ts";
import axios from "axios";
import {useEffect, useState} from "react";


export default function useChanges() {
    const [changes, setChanges] = useState<Change[]>([]);

    function fetchChanges():void {
        axios.get('/api/products/changelog')
            .then(response => {
                setChanges(response.data);
            })
            .catch(error => {
                console.error('Es gab ein Problem beim Abrufen der Changes:', error.message);
            });
    }

    useEffect(()=> fetchChanges(), []);

    return {
        changes,
        fetchChanges
    }
}