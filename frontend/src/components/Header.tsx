import {Link} from "react-router-dom";
import './Header.css';


export default function Header(){

    return (
        <header className={"head"}>
            <Link to={"/"}>Home</Link>
            <Link to={"/products/add"}>Produkt Hinzufügen</Link>
            <Link to={"/critical"}>Kritische Produkte</Link>
        </header>
    )
}