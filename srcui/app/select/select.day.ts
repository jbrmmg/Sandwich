import {IIngredient} from '../management/ingredient/ingredient';

export class SelectDay {
    day: string;
    month: string;
    date: number;
    locked: boolean;
    sandwich: IIngredient[];
}
