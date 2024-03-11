import './SearchBar.css';
import {ChangeEvent} from "react";
import {Product} from "../../types/Product.ts";

type SearchBarProps = {
    setResult: (products:Product[]) => void;
    products: Product[];
    handleSearchText: (searchText:string) => void;
    handleFilterChange: (filter:string) => void;
}

export default function SearchBar(props: Readonly<SearchBarProps>):JSX.Element{


    function handleSearchText(event: ChangeEvent<HTMLInputElement>):void{
        const searchText = event.target.value.toLowerCase();
        props.handleSearchText(searchText);
    }

    function handleFilterChange(event: ChangeEvent<HTMLSelectElement>):void{
        props.handleFilterChange(event.target.value);
    }

    return (
        <div className={"searchBar"}>
            <input onChange={handleSearchText}/>
            <select defaultValue={"all"} onChange={handleFilterChange}>
                <option value="all">Alle</option>
                <option value="name">Name</option>
                <option value="description">Beschreibung</option>
                <option value="productNumber">Artikelnummer</option>
            </select>
        </div>
    );
}