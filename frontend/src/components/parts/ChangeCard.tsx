import './ChangeCard.css';
import {Change} from "../../types/Change.ts";
import React from "react";

type ChangeCardProps = {
    change:Change;
}
export default function ChangeCard(props:Readonly<ChangeCardProps>): React.ReactElement {
    return (
      <tr className={"changeCard"}>
          <td><p>{props.change.date}</p></td>
          <td><p>{props.change.productId}</p></td>
          <td><p>{props.change.description}</p></td>
          <td><p>{props.change.type}</p></td>
          <td><p>{props.change.status}</p></td>
      </tr>
    );
}