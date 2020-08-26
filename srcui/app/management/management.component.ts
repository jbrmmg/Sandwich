import { Component } from '@angular/core';

@Component({
    templateUrl: './management.component.html',
    styleUrls: ['./management.component.css']
})
export class ManagementComponent {
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
