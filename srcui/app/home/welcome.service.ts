import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {SelectUser} from '../select/select.user';
import {environment} from '../../environments/environment';
import {catchError, tap} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class WelcomeService {
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

    getUsers(): Observable<SelectUser[]> {
        return this._http.get<SelectUser[]>(environment.production ? `users` : `api/welcome/users.json` ).pipe (
            tap(data => console.log(`All ${JSON.stringify(data)}`)),
            catchError( err => WelcomeService.handleError(err))
        );
    }
}
