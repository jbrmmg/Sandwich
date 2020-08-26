import { Component } from '@angular/core';

@Component({
    templateUrl: './ingredient.type.manage.component.html',
    styleUrls: ['./ingredient.type.manage.component.css']
})
export class IngredientTypeManageComponent {
    textData1: string;
    textData2: string;

    constructor() {
    }

    onClick() {
        // Reset
        this.textData1 = '';
        this.textData2 = '';
    }
}
