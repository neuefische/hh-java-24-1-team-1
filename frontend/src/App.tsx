import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/ProductUpdate.tsx";
import AddProductCard from "./components/AddProductCard.tsx";
import ProductDetail from "./components/ProductDetail";
import {ProductList} from "./components/ProductList.tsx";

export default function App() {

    return (
        <main>
            <Routes>
                <Route path={"/"} element={<ProductList />} />
                <Route path={"/products/add"} element={<AddProductCard />}/>
                <Route path={"/product/:id"} element={<ProductUpdate />}/>
                <Route path={"/products/:id"} element={<ProductDetail/>}/>
            </Routes>
        </main>
    )
}
