import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/main/ProductUpdate.tsx";
import ProductDetail from "./components/main/ProductDetail.tsx";
import {ProductList} from "./components/main/ProductList.tsx";
import Header from "./components/utility/Header.tsx";
import AddProductGallery from "./components/main/AddProductGallery.tsx";
import useProducts from "./hooks/useProducts.ts";
import CriticalProductList from "./components/main/CriticalProductList.tsx";

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
