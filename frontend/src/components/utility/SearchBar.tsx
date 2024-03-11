import './SearchBar.css';
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {Product} from "../../types/Product.ts";

type SearchBarProps = {
    setResult: (products:Product[]) => void;
    products: Product[];
}

export default function SearchBar(props: Readonly<SearchBarProps>):JSX.Element{
    const [selectedFilter, setSelectedFilter] = useState<string>("all");
    const [searchText, setSearchText] = useState<string>("");
    const [isLoading, setIsLoading] = useState<boolean>(true);

    function handleSubmit(event: FormEvent<HTMLFormElement>):void{
        event.preventDefault();
    }

    function handleSearchText(event: ChangeEvent<HTMLInputElement>):void{
        const searchText = event.target.value.toLowerCase();
        setSearchText(searchText);
    }

    function handleFilterChange(event: ChangeEvent<HTMLSelectElement>):void{
        setSelectedFilter(event.target.value);
    }

    useEffect(() => {
        setIsLoading(true);
        props.setResult(
            props.products.filter(
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
        )
        setIsLoading(false);
    }, [searchText, selectedFilter, props.products]);

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <form onSubmit={handleSubmit} className={"searchBar"}>
            <input onChange={handleSearchText}/>
            <select defaultValue={"all"} onChange={handleFilterChange}>
                <option value="all">Alle</option>
                <option value="name">Name</option>
                <option value="description">Beschreibung</option>
                <option value="productNumber">Artikelnummer</option>
            </select>
        </form>
    );
}