import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/ProductUpdate.tsx";
import ProductDetail from "./components/ProductDetail";
import {ProductList} from "./components/ProductList.tsx";
import Header from "./components/Header.tsx";
import AddProductGallery from "./components/AddProductGallery.tsx";
import {FormEvent} from "react";
import {ProductDTO} from "./types/ProductDTO.ts";
import axios from "axios";
import {RestfulUtility} from "../types/RestfulUtilit.ts"
import {Product} from "./types/Product.ts";

export default function App() {


    function postNewProduct(event:FormEvent<HTMLFormElement>, newProduct:ProductDTO):Product {
        event.preventDefault();
        axios.post('../api/products', newProduct)
            .then(response => {
                return response.data;
                console.log("New product added with id " + response.data.id + ".");
            })
            .catch(error => {
                console.error("Error creating product: ", error.message);
            })
    }


    const restfulUtility:RestfulUtility = {
        getById: () => void,
        getAll: () => void,
        post: postNewProduct,
        put: () => void,
        delete: () => void
    }


    return (
        <>
            <Header />
            <main>
                <Routes>
                    <Route path={"/"} element={<ProductList />} />
                    <Route path={"/products/add"} element={<AddProductGallery />}/>
                    <Route path={"/products/:id/edit"} element={<ProductUpdate />}/>
                    <Route path={"/products/:id"} element={<ProductDetail/>}/>
                </Routes>
            </main>
        </>
    )
}
