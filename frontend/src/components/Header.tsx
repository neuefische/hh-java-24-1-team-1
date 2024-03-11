import {Link} from "react-router-dom";
import './Header.css';


export default function Header(){

    return (
        <header className={"header"}>
            <Link className={"link"} to={"/"}>Home</Link>
            <Link className={"link"} to={"/products/add"}>Produkt Hinzufügen</Link>
            <Link className={"link"} to={"/critical"}>Kritische Produkte</Link>
        </header>
    )
}