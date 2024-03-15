import {useEffect, useRef} from 'react';
import JsBarcode from 'jsbarcode';

type BarcodeProps = {
    barcode: string;
}

export default function Barcode({ barcode }: BarcodeProps) {
    const barcodeRef = useRef(null);

    function hasValidLength(barcode: string) {
        return barcode.length <= 64 && barcode.length > 0;
    }

    useEffect(() => {
        if (barcodeRef.current && barcode && hasValidLength(barcode)) {
            JsBarcode(barcodeRef.current, barcode, {
                format: "CODE128",
            });
        }
    }, [barcode]);

    return <svg ref={barcodeRef}/>
}