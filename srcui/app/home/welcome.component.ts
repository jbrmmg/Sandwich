import {Component, OnInit} from '@angular/core';
import {WelcomeService} from './welcome.service';
import {SelectUser} from '../select/select.user';
import {SelectService} from '../select/select.service';
import {IIngredient} from '../management/ingredient/ingredient';

@Component({
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {
  users: SelectUser[];
  ingredients: IIngredient[];

  constructor(private readonly _welcomeService: WelcomeService,
              private readonly _userService: SelectService ) {
  }

  ingredientString(sandwich: IIngredient[]): string {
      let firstDone = false;
      let result = "";

      for(let nextIngredient of sandwich) {
          if(firstDone) {
              result = result + ', ';
          }

          result = result + nextIngredient.name;

          firstDone = true;
      }

      return result;
  }

  ngOnInit() {
    this._welcomeService.getUsers().subscribe(
        users => this.users = users,
        error => console.log(`Failed to get the users ${error}.`),
        () => {
          if(this.users && this.users.length > 0) {
            for(let nextUser of this.users) {
              if(nextUser.days && nextUser.days.length > 0) {
                nextUser.days.sort((d1,d2) => {
                  if( this._userService.dayToInt(d1.day) > this._userService.dayToInt(d2.day) ) {
                    return 1;
                  } else if ( this._userService.dayToInt(d1.day) === this._userService.dayToInt(d2.day) ) {
                    return 0;
                  }

                  return -1;
                } );
              }
            }
          }
          console.log(`Finished get users.`)
        }
    );

    this._welcomeService.getRequiredIngredients().subscribe(
      ingredients => this.ingredients = ingredients,
        error => console.log(`Failed to get the users ${error}.`),
        () => console.log(`Finished get users.`)
    );
  }
}
