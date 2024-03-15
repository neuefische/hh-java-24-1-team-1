import {Route, Routes} from "react-router-dom";
import ProductUpdate from "./components/product/main/ProductUpdate.tsx";
import ProductDetail from "./components/product/main/ProductDetail.tsx";
import {ProductList} from "./components/product/main/ProductList.tsx";
import Header from "./components/utility/Header.tsx";
import AddProductGallery from "./components/product/main/AddProductGallery.tsx";
import useProducts from "./hooks/useProducts.ts";
import CriticalProductList from "./components/product/main/CriticalProductList.tsx";
import React, {useEffect, useState} from "react";
import {Product} from "./types/Product.ts";
import {ProductChangeLog} from "./components/product/main/ProductChangeLog.tsx";
import useProductChanges from "./hooks/useProductChanges.ts";
import useStorageSpace from "./hooks/useStorageSpace.ts";
import StorageSpaceGallery from "./components/storageSpace/main/StorageSpaceGallery.tsx";

export default function App(): React.ReactElement {
    const {products, saveProduct, getProductById, updateProduct, deleteProduct} = useProducts();
    const {storageSpaces, getEmptyStorageSpaces, getOccupiedStorageSpaces, getOccupiedStorageSpacesCount} = useStorageSpace();
    const {changes, fetchChanges} = useProductChanges();
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

    // eslint-disable-next-line react-hooks/exhaustive-deps
    useEffect(fetchChanges,[products]);

    return (
        <>
            <Header homeHook={() => setSearchText("")} criticalHook={() => setSearchText("")}/>
            <main>
                <Routes>
                    <Route path={"/"} element={<ProductList handleFilterChange={setSelectedFilter} handleSearchText={setSearchText} products={filteredProducts} />} />
                    <Route path={"/products/add"} element={<AddProductGallery saveProduct={saveProduct}/>}/>
                    <Route path={"/products/:id/edit"} element={<ProductUpdate updateProduct={updateProduct} getProductById={getProductById}/>}/>
                    <Route path={"/products/:id"} element={<ProductDetail getProductById={getProductById} deleteProduct={deleteProduct}/>}/>
                    <Route path={"/critical"} element={<CriticalProductList handleFilterChange={setSelectedFilter} handleSearchText={setSearchText}  products={filteredProducts}/>} />
                    <Route path={"/products/changelog"} element={<ProductChangeLog changes={changes}/>} />

                    <Route path={"/storage"} element={<StorageSpaceGallery storageSpaces={storageSpaces} getEmptyStorageSpaces={getEmptyStorageSpaces} getOccupiedStorageSpaces={getOccupiedStorageSpaces} getOccupiedStorageSpacesCount={getOccupiedStorageSpacesCount}/>}/>
                </Routes>
            </main>
        </>
    )
}
