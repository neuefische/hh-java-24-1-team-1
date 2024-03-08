import './SearchBar.css';
import {ChangeEvent} from "react";

type SearchBarProps = {
    handleSearchText: (searchText: string) => void,
}

export default function SearchBar(props: Readonly<SearchBarProps>){

    function handleSearchText(event: ChangeEvent<HTMLInputElement>){
        props.handleSearchText(event.target.value)
    }

    return(
        <div className={"searchBar"}>
            <input  onChange={handleSearchText}/>
        </div>
    );
}