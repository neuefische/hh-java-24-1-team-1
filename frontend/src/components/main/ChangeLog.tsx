import './ChangeLog.css';
import React from 'react';
import {Change} from "../../types/Change.ts";
import ChangeCard from "../parts/ChangeCard.tsx";

type ChangeLogProps = {
    changes:Change[];
}

export function ChangeLog(props:Readonly<ChangeLogProps>): React.ReactElement {
    return (
        <main className={"changeLog"}>
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
                    {props.changes.map((change:Change) => (<ChangeCard key={change.date} change={change}/>))}
                </tbody>
            </table>
        </main>
    );
}