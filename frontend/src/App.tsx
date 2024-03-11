import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/ProductUpdate.tsx";
import ProductDetail from "./components/ProductDetail";
import {ProductList} from "./components/ProductList.tsx";
import Header from "./components/Header.tsx";
import AddProductGallery from "./components/AddProductGallery.tsx";
import useProducts from "./hooks/useProducts.ts";
import CriticalProductList from "./components/CriticalProductList.tsx";

export default function App() {
    const {products, saveProduct, getProductById, updateProduct, deleteProduct} = useProducts();

    return (
        <>
            <Header />
            <main>
                <Routes>
                    <Route path={"/"} element={<ProductList products={products} />} />
                    <Route path={"/products/add"} element={<AddProductGallery saveProduct={saveProduct}/>}/>
                    <Route path={"/products/:id/edit"} element={<ProductUpdate updateProduct={updateProduct} getProductById={getProductById}/>}/>
                    <Route path={"/products/:id"} element={<ProductDetail getProductById={getProductById} deleteProduct={deleteProduct}/>}/>
                    <Route path={"/critical"} element={<CriticalProductList products={products}/>} />
                </Routes>
            </main>
        </>
    )
}
