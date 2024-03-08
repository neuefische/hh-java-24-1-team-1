import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/ProductUpdate.tsx";
import ProductDetail from "./components/ProductDetail";
import {ProductList} from "./components/ProductList.tsx";
import Header from "./components/Header.tsx";
import AddProductGallery from "./components/AddProductGallery.tsx";
import {useEffect, useState} from "react";
import {ProductDTO} from "./types/ProductDTO.ts";
import axios from "axios";
import {RestfulUtility} from "./types/RestfulUtility.ts"
import {Product} from "./types/Product.ts";

export default function App() {
    const [products, setProducts] = useState<Product[]>([]);
    function getAllProducts():void {
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
        return {id:id, name:'', amount:0, description:'', productNumber:'', minimumStockLevel:0};
    }

    function postProduct(newProduct:ProductDTO) {
        axios.post('/api/products', newProduct)
            .then((response) => {
                console.log("New product added with id " + response.data.id + ".");
            })
            .catch(error => {
                console.error("Error creating product: ", error.message);
            })
    }


    function putProduct(updatedProduct:Product) {
        axios.put(`/api/products/update`, updatedProduct)
            .then((response) => {
                console.log(response)
                getAllProducts();
                return response.data;
            })
            .catch(error => {
                console.log(error)
            });
        return getProductById(updatedProduct.id);
    }

    function deleteProductById(id:string):void{
        axios.delete(`/api/products/${id}`)
            .then(getAllProducts)
            .catch(error => {
                console.log(error)
            });
    }

    const restfulUtility:RestfulUtility = {
        getAllProducts: getAllProducts,
        getProductById: getProductById,
        postProduct: postProduct,
        putProduct: putProduct,
        deleteProductById: deleteProductById
    }

    useEffect(
        getAllProducts, []
    )

    return (
        <>
            <Header />
            <main>
                <Routes>
                    <Route path={"/"} element={<ProductList products={products} restfulUtility={restfulUtility}/>} />
                    <Route path={"/products/add"} element={<AddProductGallery restfulUtility={restfulUtility}/>}/>
                    <Route path={"/products/:id/edit"} element={<ProductUpdate restfulUtility={restfulUtility}/>}/>
                    <Route path={"/products/:id"} element={<ProductDetail restfulUtility={restfulUtility}/>}/>
                </Routes>
            </main>
        </>
    )
}
