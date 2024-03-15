import { useEffect, useRef } from 'react';
import JsBarcode from 'jsbarcode';

type BarcodeProps = {
    barcode: string;
}

export default function Barcode({ barcode }: BarcodeProps) {
    const barcodeRef = useRef(null);

    useEffect(() => {
    if (barcodeRef.current && barcode) {
        JsBarcode(barcodeRef.current, barcode, {
            format: "CODE39",
        });
    }
}, [barcode]);

    return <svg ref={barcodeRef}></svg>;
}