import './ProductChangeLog.css';
import React from 'react';
import {ProductChange} from "../../../types/ProductChange.ts";
import ProductChangeCard from "../parts/ProductChangeCard.tsx";

type ChangeLogProps = {
    changes:ProductChange[];
}

export function ProductChangeLog(props:Readonly<ChangeLogProps>): React.ReactElement {
    return (
        <main className={"productChangeLog"}>
            <table className={"productChangeLogMainTable"}>
                <thead className={"productChangeLogMainTableHead"}>
                    <tr>
                        <th>Datum</th>
                        <th>Beschreibung</th>
                        <th>Typ</th>
                        <th>Status</th>
                        <th className={"productChangeLogProductColumn"}>
                            <table className={"productChangeLogProductTable"}>
                                <thead className={"productChangeLogProductTableHead"}>
                                <tr>
                                    <th>Name</th>
                                    <th>Artikel Nr.</th>
                                    <th>Auf Lager</th>
                                    <th>Lagerplatz</th>
                                    <th>Details</th>
                                </tr>
                                </thead>
                            </table>
                        </th>
                    </tr>
                </thead>

                <tbody>
                {props.changes.map((change: ProductChange) => (<ProductChangeCard key={change.date} change={change}/>))}
                </tbody>
            </table>
        </main>
    );
}