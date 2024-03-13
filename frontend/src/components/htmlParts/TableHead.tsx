import React from "react";

export default function TableHead(): React.ReactElement {
    return (
        <thead>
            <tr>
                <th>Produktname</th>
                <th>Artikelnummer</th>
                <th>Anzahl auf Lager</th>
                <th>Details</th>
            </tr>
        </thead>
    );
}