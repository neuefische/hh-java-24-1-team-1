import {ChangeEvent} from "react";

type SearchBarProps = {
    handleSearchText: (searchText: string) => void,
}

export default function SearchBar(props: Readonly<SearchBarProps>){

    function handleSearchText(event: ChangeEvent<HTMLInputElement>){
        props.handleSearchText(event.target.value)
        console.log(event.target.value)
    }

    return(
        <input onChange={handleSearchText}/>
    );
}