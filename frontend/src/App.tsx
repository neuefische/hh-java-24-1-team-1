import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/ProductUpdate.tsx";
import ProductDetail from "./components/ProductDetail";
import {ProductList} from "./components/ProductList.tsx";
import Header from "./components/Header.tsx";
import AddProductGallery from "./components/AddProductGallery.tsx";
import CriticalProductList from "./components/CriticalProductList.tsx";

export default function App() {

    return (
        <>
            <Header />
            <main>
                <Routes>
                    <Route path={"/"} element={<ProductList />} />
                    <Route path={"/products/add"} element={<AddProductGallery />}/>
                    <Route path={"/products/:id/edit"} element={<ProductUpdate />}/>
                    <Route path={"/products/:id"} element={<ProductDetail/>}/>
                    <Route path={"/critical"} element={<CriticalProductList />} />
                </Routes>
            </main>
        </>
    )
}
