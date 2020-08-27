import { Component } from '@angular/core';
import {IngredientsService} from './ingredients.service';
import {IIngredient} from './ingredient';

@Component({
    templateUrl: './ingredient.manage.component.html',
    styleUrls: ['./ingredient.manage.component.css']
})
export class IngredientManageComponent {
    ingredients: IIngredient[];

    constructor (private readonly _ingredientService: IngredientsService) {
        this._ingredientService.getIngredients().subscribe(
            ingredients => {
                this.ingredients = ingredients;
            },
            error => console.log('Failed to get the ingredients.'),
            () => console.log('Finished get ingredients.')
        );
    }
}
