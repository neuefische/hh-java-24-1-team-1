import './AddProductCard.css'
import {ChangeEvent, FormEvent, useState} from "react";
import axios from "axios";

type ProductDTO = {
    name:string,
    amount:number,
    description:string
}

const initialFormData:ProductDTO = {
    name: '',
    description: '',
    amount: 0
}

export default function AddProductCard():JSX.Element{
    const [formData, setFormData] = useState<ProductDTO>(initialFormData);

    function handleSubmit(event:FormEvent<HTMLFormElement>):void {
        event.preventDefault();
        axios.post('../api/products', formData)
            .then(response => {
                console.log("New product added with id " + response.data.id + ".");
            })
            .catch(error => {
                console.error("Error creating product: ", error.message);
            })
        setFormData(initialFormData);
    }

    function handleChangeInput(event: ChangeEvent<HTMLInputElement>):void {
        setFormData(
            {
                ...formData,
                [event.target.name]: event.target.value
            }
        )
    }

    return (
            <form className={"PostBar"} onSubmit={handleSubmit}>
                <h2>Neues Produkt anlegen:</h2>
                <div>
                    <label htmlFor={"name"}>Produktname:</label>
                    <input id={"name"} name={"name"}
                           type={"text"}
                           value={formData.name}
                           onChange={handleChangeInput}/>
                </div>
                <div>
                    <label htmlFor={"description"}>Produktbeschreibung:</label>
                    <input id={"description"} name={"description"}
                           type={"text"}
                           value={formData.description}
                           onChange={handleChangeInput}/>
                </div>
                <div>
                    <label htmlFor={"amount"}>Anzahl auf Lager:</label>
                    <input id={"amount"} name={"amount"}
                           type={"number"}
                           value={formData.amount}
                           onChange={handleChangeInput}/>
                </div>
                <button type={"submit"}>Bestätigen</button>
            </form>
    )
}