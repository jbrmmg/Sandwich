import { Component } from '@angular/core';
import {IIngredientType} from './ingredient.type';
import {IngredientsService} from './ingredients.service';

@Component({
    templateUrl: './ingredient.type.manage.component.html',
    styleUrls: ['./ingredient.type.manage.component.css']
})
export class IngredientTypeManageComponent {
    ingredientTypes: IIngredientType[];

    constructor (private readonly _ingredientService: IngredientsService) {
        this._ingredientService.getIngredientTypes().subscribe(
            ingredientTypes => {
                this.ingredientTypes = ingredientTypes;
            },
            error => console.log('Failed to get the ingredient types.'),
            () => console.log('Finished get ingredient types.')
        );
    }
}
