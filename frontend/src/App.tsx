import {Route, Routes} from "react-router-dom";
import AddProductGallery from "./components/AddProductGallery.tsx";


export default function App() {

    return (
        <main>
            <Routes>
                <Route path={"/"} element={<h2>Hello World </h2>}/>
                <Route path={"/products/add"} element={<AddProductGallery triggerChange={fetchData}/>}/>
            </Routes>
        </main>
    )
}
