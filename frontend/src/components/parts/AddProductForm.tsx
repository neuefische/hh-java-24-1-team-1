import * as yup from 'Yup';
import {ProductDataSchema, FormError} from "../../types/ProductDataSchema";
import './AddProductForm.css'
import {ChangeEvent, FormEvent, useState} from "react";
import {ProductDTO} from "../../types/ProductDTO.ts";

type AddProductFormProps = {
    handleSubmit: (event:FormEvent<HTMLFormElement>, newProduct:ProductDTO) => void
}

const initialFormData:ProductDTO = {
    name: '',
    description: '',
    amount: 0,
    productNumber: '',
    minimumStockLevel: 0
}

export default function AddProductForm(props: Readonly<AddProductFormProps>):JSX.Element{
    const [formData, setFormData] = useState<ProductDTO>(initialFormData);
    const [formError, setFormError] = useState<FormError>({});
    function handleSubmit(event:FormEvent<HTMLFormElement>):void{
        event.preventDefault();
        ProductDataSchema.validate(formData, {abortEarly: false})
            .then(() => {
                props.handleSubmit(event, formData);
                setFormData(initialFormData);
                setFormError({});
            }).catch((validationErrors: yup.ValidationError) => {
            // Validation failed
            const errors = validationErrors.inner.reduce<{ [key: string]: string }>((acc, currentError) => {
                // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                // @ts-expect-error
                acc[currentError.path] = currentError.message;
                return acc;
            }, {});
            setFormError(errors);
        });
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
        <form className={"addProductForm"} onSubmit={handleSubmit}>
            <h2>Neues Produkt anlegen:</h2>
            <div>
                <label htmlFor={"name"}>Produktname:</label>
                <input id={"name"} name={"name"}
                       type={"text"}
                       value={formData.name}
                       onChange={handleChangeInput}/>
                {formError.name && <p className={"error"}>{formError.name}</p>}
            </div>
            <div>
                <label htmlFor={"description"}>Produktbeschreibung:</label>
                <input id={"description"} name={"description"}
                       type={"text"}
                       value={formData.description}
                       onChange={handleChangeInput}/>
                {formError.description && <p className={"error"}>{formError.description}</p>}
            </div>
            <div>
                <label htmlFor={"amount"}>Anzahl auf Lager:</label>
                <input id={"amount"} name={"amount"}
                       type={"number"}
                       value={formData.amount}
                       onChange={handleChangeInput}/>
                {formError.amount && <p className={"error"}>{formError.amount}</p>}
            </div>
            <div>
                <label htmlFor={"productNumber"}>Artikelnummer:</label>
                <input id={"productNumber"} name={"productNumber"}
                       type={"text"}
                       value={formData.productNumber}
                       onChange={handleChangeInput}/>
                {formError.productNumber && <p className={"error"}>{formError.productNumber}</p>}
            </div>
            <div>
                <label htmlFor={"minimumStockLevel"}>Mindestbestand:</label>
                <input id={"minimumStockLevel"} name={"minimumStockLevel"}
                       type={"number"}
                       value={formData.minimumStockLevel}
                       onChange={handleChangeInput}/>
                {formError.minimumStockLevel && <p className={"error"}>{formError.minimumStockLevel}</p>}
            </div>
            <div>
                <button type={"submit"}>Bestätigen</button>
            </div>
        </form>
    )


}