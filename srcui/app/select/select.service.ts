import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {SelectUser} from './select.user';
import {environment} from '../../environments/environment';
import {catchError, tap} from 'rxjs/operators';
import {IIngredient} from '../management/ingredient/ingredient';

@Injectable({
    providedIn: 'root'
})
export class SelectService {
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

    constructor (private readonly _http: HttpClient) {
    }

    public dayToInt(day: string): number {
        switch(day.toLocaleLowerCase()) {
            case "monday":
                return 1;
            case "tuesday":
                return 2;
            case "wednesday":
                return 3;
            case "thursday":
                return 4;
            case "friday":
                return 5;
            case "saturday":
                return 6;
            case "sunday":
                return 7;
        }

        return 0;
    }

    getUser (id: string): Observable<SelectUser> {
        return this._http.get<SelectUser>(environment.production ? `user?id=${id}` : `api/select/user${id}.json` ).pipe (
            tap(data => console.log(`All ${JSON.stringify(data)}`)),
            catchError( err => SelectService.handleError(err))
        );
    }

    saveSandwich(id: string, day: string, date: number, sandwich: IIngredient[]) {
        return this._http.put<IIngredient[]>( `sandwich?user=${id}&day=${day}&date=${date}`, sandwich).subscribe(
            () => {
                console.log('PUT call successful value returned in body');
            },
            (response) => {
                console.log('POST call in error', response);
            },
            () => {
                console.log('The POST observable is now complete (add)');
            }
        );
    }
}
