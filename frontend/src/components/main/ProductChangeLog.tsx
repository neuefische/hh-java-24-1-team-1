import './ProductChangeLog.css';
import React from 'react';
import {ProductChange} from "../../types/ProductChange.ts";
import ProductChangeCard from "../parts/ProductChangeCard.tsx";


type ChangeLogProps = {
    changes:ProductChange[];
}

export function ProductChangeLog(props:Readonly<ChangeLogProps>): React.ReactElement {
    return (
        <main className={"productList"}>
            <table>
                <thead>
                    <tr>
                        <th>Datum</th>
                        <th>Produkt ID</th>
                        <th>Beschreibung</th>
                        <th>Typ</th>
                        <th>Status</th>
                    </tr>
                </thead>

                <tbody>
                    {props.changes.map((change:ProductChange) => (<ProductChangeCard key={change.date} change={change}/>))}
                </tbody>
            </table>
        </main>
    );
}