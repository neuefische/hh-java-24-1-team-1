import {StorageSpace} from "../../../types/StorageSpace.ts";
import React from "react";

type StorageSpaceCardProps = {
    space: StorageSpace;
}

export default function StorageSpaceCard(props: Readonly<StorageSpaceCardProps>): React.ReactElement {
    return (
        <tr className={"storageSpaceCard"}>
            <td><p>{props.space.storageSpaceName}</p></td>
            <td><p>{props.space.isOccupied ? "Ja" : "Nein"}</p></td>
        </tr>
    );
}