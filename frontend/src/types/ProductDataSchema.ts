import * as yup from 'yup';

export const ProductDataSchema = yup.object().shape({
    name: yup
        .string()
        .required(),
    description: yup
        .string()
        .required(),
    amount: yup
        .number()
        .required()
        .min(0)
        .integer()
        .max(2147483647),
    productNumber: yup
        .string()
        .required(),
    minimumStockLevel: yup
        .number()
        .required()
        .min(0)
        .integer()
        .max(2147483647)
});

export const ProductDtoDataSchema = yup.object().shape({
    name: yup
        .string()
        .required(),
    description: yup
        .string()
        .required(),
    amount: yup
        .number()
        .required()
        .min(0)
        .integer()
        .max(2147483647),
    minimumStockLevel: yup
        .number()
        .required()
        .min(0)
        .integer()
        .max(2147483647)
});

export interface FormError {
    name?: string;
    description?: string;
    amount?: string;
    productNumber?: string;
    minimumStockLevel?: string;
}
