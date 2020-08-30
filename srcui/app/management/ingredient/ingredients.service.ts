import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {IIngredient} from './ingredient';
import {IIngredientType} from './ingredient.type';
import {ISetting} from '../settings/setting';
import {environment} from '../../../environments/environment';
import {catchError, tap} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class IngredientsService {
    private readonly TEST_TYPE_URL = 'api/ingredient/types.json';
    private readonly TEST_INGREDIENTS_URL = 'api/ingredient/ingredients.json';

    private readonly TYPE_URL = 'ingredients/types';
    private readonly INGREDIENT_URL = 'ingredients';

    private static handleError(err: HttpErrorResponse) {
        let errorMessage;
        if (err.error instanceof ErrorEvent) {
            errorMessage = 'An error occurred: ';
        } else {
            errorMessage = 'Server returned code ' + err.status + ', error message is: ' + err.message;
        }
        console.error(errorMessage);
        return throwError(errorMessage);
    }

    constructor(private readonly _http: HttpClient) {
    }

    getIngredients(): Observable<IIngredient[]> {
        return this._http.get<IIngredient[]>(environment.production ? this.INGREDIENT_URL : this.TEST_INGREDIENTS_URL ).pipe(
            tap(data => console.log('All: ' + JSON.stringify(data))),
            catchError( err => IngredientsService.handleError(err))
        );
    }

    getIngredientTypes(): Observable<IIngredientType[]> {
        return this._http.get<IIngredientType[]>(environment.production ? this.TYPE_URL : this.TEST_TYPE_URL ).pipe(
            tap(data => console.log('All: ' + JSON.stringify(data))),
            catchError( err => IngredientsService.handleError(err))
        );
    }
}
