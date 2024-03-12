import './ChangeCard.css';
import {Change} from "../../types/Change.ts";

type ChangeCardProps = {
    change:Change;
}
export default function ChangeCard(props:Readonly<ChangeCardProps>) {
    return (
      <div className={"changeCard"}>
          <span>{props.change.date}</span>
          <span>{props.change.productId}</span>
          <span>{props.change.description}</span>
          <span>{props.change.type}</span>
          <span>{props.change.status}</span>
      </div>
    );
}