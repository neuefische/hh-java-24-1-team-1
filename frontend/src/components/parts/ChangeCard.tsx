import './ChangeCard.css';
import {Change} from "../../types/Change.ts";

type ChangeCardProps = {
    change:Change;
}
export default function ChangeCard(props:Readonly<ChangeCardProps>) {
    return (
      <div className={"changeCard"}>
          <p>{props.change.date}</p>
          <p>{props.change.productId}</p>
          <p>{props.change.description}</p>
          <p>{props.change.type}</p>
          <p>{props.change.status}</p>
      </div>
    );
}