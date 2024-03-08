import {Link} from "react-router-dom";


export default function Header(){

    return (
        <header style={{
            border: '1px solid #ddd',
            padding: '10px',
            borderRadius: '5px',
            margin: '20px 10px',
            display: 'flex',
            justifyContent: 'space-evenly',
            alignItems: 'center',
            backgroundColor: '#f8f8f8'
        }}>
            <Link to={"/"}>Home</Link>
            <Link to={"/products/add"}>Produkt Hinzufügen</Link>
            <Link to={"/critical"}>Kritische Produkte</Link>
        </header>
    )
}