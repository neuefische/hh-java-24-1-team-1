import './ProductChangeLog.css';
import React from 'react';
import {ProductChange} from "../../../types/ProductChange.ts";
import ProductChangeCard from "../../parts/ProductChangeCard.tsx";
import TableHead from "../../htmlParts/TableHead.tsx";

type ChangeLogProps = {
    changes:ProductChange[];
}

export function ProductChangeLog(props:Readonly<ChangeLogProps>): React.ReactElement {
    return (
        <main className={"changeLog"}>
            <table>
                <thead>
                    <tr>
                        <th>Datum</th>
                        <th>Beschreibung</th>
                        <th>Typ</th>
                        <th>Status</th>
                        <th>                <TableHead/>
                        </th>
                    </tr>
                </thead>

                <tbody>
                    {props.changes.map((change:ProductChange) => (<ProductChangeCard key={change.date} change={change}/>))}
                </tbody>
            </table>
        </main>
    );
}