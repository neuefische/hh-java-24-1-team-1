import {ProductChange} from "../types/ProductChange.ts";
import axios from "axios";
import {useEffect, useState} from "react";


export default function useProductChanges() {
    const [changes, setChanges] = useState<ProductChange[]>([]);

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