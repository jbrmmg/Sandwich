import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {throwError} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class IngredientsService {
    private readonly TEST_TYPE_URL = 'api/ingredient/types.json';
    private readonly TEST_INGREDIENTS_URL = 'api/ingredient/ingredients.json';

    private readonly TYPE_URL = 'ingredients/type';
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
}
