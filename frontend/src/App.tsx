import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/ProductUpdate.tsx";
import AddProductCard from "./components/AddProductCard.tsx";
import ProductDetail from "./components/ProductDetail";
import {ProductList} from "./components/ProductList.tsx";
import Header from "./components/Header.tsx";

export default function App() {

    return (
        <>
            <Header />
            <main>
                <Routes>
                    <Route path={"/"} element={<ProductList />} />
                    <Route path={"/products/add"} element={<AddProductCard />}/>
                    <Route path={"/products/:id/edit"} element={<ProductUpdate />}/>
                    <Route path={"/products/:id"} element={<ProductDetail/>}/>
                </Routes>
            </main>
        </>
    )
}
