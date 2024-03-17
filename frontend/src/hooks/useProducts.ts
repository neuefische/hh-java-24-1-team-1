import {ProductDTO} from "../types/ProductDTO.ts";
import {Product} from "../types/Product.ts";
import axios from "axios";
import {useEffect, useState} from "react";

export default function useProducts() {
    const [products, setProducts] = useState<Product[]>([]);

    function fetchProducts():void {
        axios.get('/api/products')
            .then(response => {
                setProducts(response.data);
            })
            .catch(error => {
                console.error('Es gab ein Problem beim Abrufen der Produkte:', error.message);
            });
    }

    function getProductById(id:string):Product {
        const productWithId:Product[] = products.filter((product:Product) => product.id === id)

        if(productWithId.length === 0) console.error('Kein Produkt mit der ID ' + id + ' gefunden.');
        else return productWithId[0];
        return {id:id, storageSpaceName:'', name:'', amount:0, description:'', productNumber:'', minimumStockLevel:0};
    }

    function saveProduct(newProduct:ProductDTO) {
        axios.post('/api/products', newProduct)
            .then((response) => {
                console.log("New product added with id " + response.data.id + ".");
                fetchProducts();
            })
            .catch(error => {
                console.error("Error creating product: ", error.message);
            })
    }


    function updateProduct(updatedProduct: Product) {
        axios.put(`/api/products/${updatedProduct.id}`, updatedProduct)
            .then(() => {
                fetchProducts();
            })
            .catch(error => {
                if (error?.response?.status === 400) {
                    alert('Fehler: ' + error.response.data.errorMsg);
                } else {
                    alert('Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.');
                }
            });
    }
    function deleteProduct(id:string):void{
        axios.delete(`/api/products/${id}`)
            .then(fetchProducts)
            .catch(error => {
                console.log(error)
            });
    }

    useEffect(()=> fetchProducts(), []);

    return {
        products,
        saveProduct,
        getProductById,
        updateProduct,
        deleteProduct
    }
}
