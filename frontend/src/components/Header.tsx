import {Link} from "react-router-dom";
import './Header.css';


export default function Header(){

    return (
        <header className={"head"}>
            <a href={"/"}>Home</a>
            <Link to={"/products/add"}>Produkt Hinzufügen</Link>
            <a href={"/critical"}>Kritische Produkte</a>
        </header>
    )
}