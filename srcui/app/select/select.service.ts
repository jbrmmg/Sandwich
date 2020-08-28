import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {SelectUser} from './select.user';
import {environment} from '../../environments/environment';
import {catchError, tap} from 'rxjs/operators';
import {SelectSandwich} from './select.sandwich';

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

    getUser (id: string): Observable<SelectUser> {
        return this._http.get<SelectUser>(environment.production ? `user?id=${id}` : `api/select/user${id}.json` ).pipe (
            tap(data => console.log(`All ${JSON.stringify(data)}`)),
            catchError( err => SelectService.handleError(err))
        );
    }

    saveSandwich(id: string, day: string, sandwich: SelectSandwich) {
        return this._http.put<SelectSandwich>( `sandwich?user=${id}&day=${day}`, sandwich).subscribe(
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
