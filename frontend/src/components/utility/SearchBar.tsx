import './SearchBar.css';
import {ChangeEvent, FormEvent, useState} from "react";
import {Product} from "../../types/Product.ts";

type SearchBarProps = {
    setResult: (products:Product[]) => void;
    products: Product[];
}

export default function SearchBar(props: Readonly<SearchBarProps>):JSX.Element{
    const [selectedFilter, setSelectedFilter] = useState<string>("name");

    function handleSubmit(event: FormEvent<HTMLFormElement>):void{
        event.preventDefault();
    }

    function handleSearchText(event: ChangeEvent<HTMLInputElement>):void{
        const searchText = event.target.value.toLowerCase();
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
                })
        )
    }

    function handleFilterChange(event: ChangeEvent<HTMLSelectElement>):void{
        setSelectedFilter(event.target.value);
    }

    return (
        <form onSubmit={handleSubmit} className={"searchBar"}>
            <input onChange={handleSearchText}/>
            <select onChange={handleFilterChange}>
                <option value="all">Alle</option>
                <option value="name">Name</option>
                <option value="description">Beschreibung</option>
                <option value="productNumber">Artikelnummer</option>
            </select>
        </form>
    );
}