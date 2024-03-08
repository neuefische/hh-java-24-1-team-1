import {Link} from "react-router-dom";
import './Header.css';


export default function Header(){

    return (
        <header>
            <Link className={"link"} to={"/"}><h2>Home</h2></Link>
            <Link className={"link"} to={"/products/add"}><h2>Produkt Hinzufügen</h2></Link>
        </header>
    )
}