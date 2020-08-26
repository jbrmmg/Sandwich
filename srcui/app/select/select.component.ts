import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    templateUrl: './select.component.html',
    styleUrls: ['./select.component.css']
})
export class SelectComponent {
    textData1: string;
    textData2: string;
    whoIsIt: string;

    constructor(private route: ActivatedRoute) {
        this.route.params.subscribe( params => {
            this.whoIsIt = params['id'];
        } );
    }

    get who(): string {
        return this.whoIsIt;
    }

    onClick() {
        // Reset
        this.textData1 = '';
        this.textData2 = '';
    }
}
