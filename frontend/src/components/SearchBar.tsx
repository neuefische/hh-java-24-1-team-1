import './SearchBar.css';
import {ChangeEvent, FormEvent} from "react";
import {Product} from "../types/Product.ts";

type SearchBarProps = {
    setResult: (products:Product[]) => void;
    products: Product[];
}

export default function SearchBar(props: Readonly<SearchBarProps>):JSX.Element{

    function handleSubmit(event: FormEvent<HTMLFormElement>):void{
        event.preventDefault();
    }

    function handleSearchText(event: ChangeEvent<HTMLInputElement>):void{
        props.setResult(
            props.products.filter(
                (product:Product) =>
                    product.name.toLowerCase().includes(event.target.value.toLowerCase())
                    || product.description.toLowerCase().includes(event.target.value.toLowerCase()))
        )
    }

    return(
        <form onSubmit={handleSubmit} className={"searchBar"}>
            <input onChange={handleSearchText}/>
        </form>
    );
}