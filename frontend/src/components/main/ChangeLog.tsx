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
            {props.changes.map((change:Change) => (<ChangeCard key={change.date} change={change}/>))}
        </main>
    );
}