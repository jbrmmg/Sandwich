import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {SelectUser} from './select.user';
import {SelectService} from './select.service';
import {SelectDay} from './select.day';
import {IngredientsService} from '../management/ingredient/ingredients.service';
import {IIngredientType} from '../management/ingredient/ingredient.type';
import {IIngredient} from '../management/ingredient/ingredient';

@Component({
    templateUrl: './select.component.html',
    styleUrls: ['./select.component.css']
})
export class SelectComponent implements OnInit {
    private selectedDayIndex: number;
    user: SelectUser;
    ingredientTypes: IIngredientType[];
    ingredients: IIngredient[];

    constructor ( private readonly _userService: SelectService,
                  private readonly _ingredientService: IngredientsService,
                  private readonly route: ActivatedRoute) {
    }

    ngOnInit() {
        this.route.params.subscribe( params => {
            this._userService.getUser(params['id']).subscribe(
                selectedUser => {
                    this.user = selectedUser;
                    this.selectedDayIndex = 0;
                },
                error => console.log(`Failed to get the user ${params['id']} ${error}.`),
                () => console.log(`Finished get user ${params['id']}.`)
            );
        });

        this._ingredientService.getIngredientTypes().subscribe(
            types => {
                this.ingredientTypes = types;
                this.ingredientTypes.sort( (t1,t2) => {
                    if (t1.order > t2.order) {
                        return 1;
                    } else if(t1.order === t2.order) {
                        return 0;
                    }

                    return -1;
                } );
            },
            error => console.log(`Failed to load ingredient types ${error}`),
            () => console.log(`Loaded ingredient types ${this.ingredientTypes.length}`)
        );

        this._ingredientService.getIngredients().subscribe(
            ingredients => this.ingredients = ingredients,
            error => console.log(`Failed to load ingredients ${error}.`),
            () => console.log(`Loaded ingredients ${this.ingredients.length}`)
        );
    }

    get selectedDay(): SelectDay {
        if (this.user != null && this.selectedDayIndex >= 0 && this.selectedDayIndex < this.user.days.length) {
            return this.user.days[this.selectedDayIndex];
        }

        return null;
    }

    moveNext() {
        this.save();
        this.selectedDayIndex++;
        if(this.selectedDayIndex >= this.user.days.length) {
            this.selectedDayIndex = 0;
        }
    }

    movePrev() {
        this.save();
        this.selectedDayIndex--;
        if(this.selectedDayIndex < 0) {
            this.selectedDayIndex = this.user.days.length - 1;
        }
    }

    private selectedTypeIndex(ingredient: IIngredient): number {
        let currentIndex = 0;
        for(let nextSelected of this.selectedDay.sandwich.ingredients) {
            if(nextSelected.type.id === ingredient.type.id) {
                return currentIndex;
            }
            currentIndex++;
        }

        return -1;
    }

    private selectedIndex(ingredient: IIngredient): number {
        let currentIndex = 0;
        for(let nextSelected of this.selectedDay.sandwich.ingredients) {
            if(nextSelected.id === ingredient.id) {
                return currentIndex;
            }
            currentIndex++;
        }

        return -1;
    }

    private selectOneIngredient(ingredient: IIngredient) {
        // If the current sandwich has an ingredient of this type, then it must be removed.
        let selectedIndex = this.selectedTypeIndex(ingredient);

        if(selectedIndex != -1) {
            this.selectedDay.sandwich.ingredients.splice(selectedIndex,1);
        }

        this.selectedDay.sandwich.ingredients.push(ingredient);
    }

    private selectManyIngredient(ingredient: IIngredient) {
        // If the current sandwich has this ingredient then remove it, otherwise add it.
        let selectedIndex = this.selectedIndex(ingredient);

        if(selectedIndex != -1) {
            this.selectedDay.sandwich.ingredients.splice(selectedIndex,1);
        } else {
            this.selectedDay.sandwich.ingredients.push(ingredient);
        }
    }

    selectIngredient(ingredient: IIngredient) {
        switch(ingredient.type.selection) {
            case 'one':
                this.selectOneIngredient(ingredient);
                break;
            case 'many':
            case 'yesno':
                this.selectManyIngredient(ingredient);
                break;
        }
    }

    isIngredientSelected(ingredient: IIngredient): boolean {
        // Is this ingredient, in the current sandwich?
        for(let nextSelected of this.selectedDay.sandwich.ingredients) {
            if(nextSelected.id === ingredient.id) {
                return true;
            }
        }

        return false;
    }

    clear(): void {
        this.selectedDay.sandwich.ingredients = [];
    }


    save(): void {
        this._userService.saveSandwich(this.user.id, this.selectedDay.day, this.selectedDay.sandwich);
    }
}
