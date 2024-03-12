import './ChangeCard.css';
import {Change} from "../../types/Change.ts";

type ChangeCardProps = {
    change:Change;
}
export default function ChangeCard(props:Readonly<ChangeCardProps>) {
    return (
      <div className={"changeCard"}>
          <h3>{props.change.id}</h3>
          <p>{props.change.date}</p>
          <p>{props.change.description}</p>
      </div>
    );
}