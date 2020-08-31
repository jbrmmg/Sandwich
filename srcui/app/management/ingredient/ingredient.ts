import {IIngredientType} from './ingredient.type';

export interface IIngredient {
    id: number;
    name: string;
    type: IIngredientType;
    count: number;
}
