import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/main/ProductUpdate.tsx";
import ProductDetail from "./components/main/ProductDetail.tsx";
import {ProductList} from "./components/main/ProductList.tsx";
import Header from "./components/utility/Header.tsx";
import AddProductGallery from "./components/main/AddProductGallery.tsx";
import useProducts from "./hooks/useProducts.ts";
import CriticalProductList from "./components/main/CriticalProductList.tsx";
import {useState} from "react";
import {Product} from "./types/Product.ts";

export default function App() {
    const {products, saveProduct, getProductById, updateProduct, deleteProduct} = useProducts();
    const [selectedFilter, setSelectedFilter] = useState<string>("all");
    const [searchText, setSearchText] = useState<string>("");

    const filteredProducts =
    products.filter(
        (product:Product) => {
            if (selectedFilter === "name") {
                return product.name.toLowerCase().includes(searchText);
            } else if (selectedFilter === "description") {
                return product.description.toLowerCase().includes(searchText);
            } else if (selectedFilter === "productNumber"){
                return product.productNumber.toLowerCase().includes(searchText);
            }
            else if (selectedFilter === "all") {
                return product.name.toLowerCase().includes(searchText) ||
                    product.description.toLowerCase().includes(searchText) ||
                    product.productNumber.toLowerCase().includes(searchText);
            }
            return false;
        }
    )

    return (
        <>
            <Header />
            <main>
                <Routes>
                    <Route path={"/"} element={<ProductList handleFilterChange={setSelectedFilter} handleSearchText={setSearchText} products={filteredProducts} />} />
                    <Route path={"/products/add"} element={<AddProductGallery saveProduct={saveProduct}/>}/>
                    <Route path={"/products/:id/edit"} element={<ProductUpdate updateProduct={updateProduct} getProductById={getProductById}/>}/>
                    <Route path={"/products/:id"} element={<ProductDetail getProductById={getProductById} deleteProduct={deleteProduct}/>}/>
                    <Route path={"/critical"} element={<CriticalProductList handleFilterChange={setSelectedFilter} handleSearchText={setSearchText}  products={filteredProducts}/>} />
                </Routes>
            </main>
        </>
    )
}
