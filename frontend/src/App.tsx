import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/ProductUpdate.tsx";
import AddProductCard from "./components/AddProductCard.tsx";


export default function App() {

    return (
        <main>
            <Routes>
                <Route path={"/"} element={<h2>Hello World </h2>}/>
                <Route path={"/products/add"} element={<AddProductCard />}/>
                <Route path={"/product/:id"} element={<ProductUpdate />}/>
            </Routes>
        </main>
    )
}
