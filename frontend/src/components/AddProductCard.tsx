import './AddProductCard.css'
import {ChangeEvent, FormEvent, useState} from "react";
import {ProductDTO} from "../types/ProductDTO.ts";

type AddProductCardPorps = {
    handleSubmit: (event:FormEvent<HTMLFormElement>, newProduct:ProductDTO) => void
}

const initialFormData:ProductDTO = {
    name: '',
    description: '',
    amount: 0,
    productNumber: '',
    minimumStockLevel: 0
}

export default function AddProductCard(props: Readonly<AddProductCardPorps>):JSX.Element{
    const [formData, setFormData] = useState<ProductDTO>(initialFormData);

    function handleSubmit(event:FormEvent<HTMLFormElement>):void{
        event.preventDefault();
        props.handleSubmit(event, formData);
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
        <form className={"addProductCard"} onSubmit={handleSubmit}>
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
            <div>
                <label htmlFor={"productNumber"}>Artikelnummer:</label>
                <input id={"productNumber"} name={"productNumber"}
                       type={"text"}
                       value={formData.productNumber}
                       onChange={handleChangeInput}/>
            </div>
            <div>
                <label htmlFor={"minimumStockLevel"}>Mindestbestand:</label>
                <input id={"minimumStockLevel"} name={"minimumStockLevel"}
                       type={"number"}
                       value={formData.minimumStockLevel}
                       onChange={handleChangeInput}/>
            </div>
            <button type={"submit"}>Bestätigen</button>
        </form>
    )


}