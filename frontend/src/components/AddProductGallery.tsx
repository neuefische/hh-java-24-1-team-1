import AddProductCard from "./AddProductCard.tsx";

type AddProductGalleryProps = {
    triggerChange: () => void;
}
export default function AddProductGallery(props:Readonly<AddProductGalleryProps>):JSX.Element{
    return (
        <AddProductCard triggerChange={props.triggerChange} />
    )
}