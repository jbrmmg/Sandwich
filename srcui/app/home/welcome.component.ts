import { Component } from '@angular/core';

@Component({
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent {
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
