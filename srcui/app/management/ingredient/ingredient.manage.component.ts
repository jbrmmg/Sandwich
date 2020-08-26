import { Component } from '@angular/core';

@Component({
    templateUrl: './ingredient.manage.component.html',
    styleUrls: ['./ingredient.manage.component.css']
})
export class IngredientManageComponent {
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
