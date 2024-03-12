import {Link} from "react-router-dom";
import './Header.css';

type HeaderProps = {
    homeHook: () => void;
    criticalHook: () => void;
}
export default function Header(props: Readonly<HeaderProps>){

    return (
        <header className={"header"}>
            <Link className={"link"} to={"/"} onClick={() => props.homeHook()}>Home</Link>
            <Link className={"link"} to={"/products/add"}>Produkt Hinzufügen</Link>
            <Link className={"link"} to={"/critical"} onClick={() => props.criticalHook()}>Kritische Produkte</Link>
        </header>
    )
}