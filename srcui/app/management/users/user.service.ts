import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {IUser} from './user';
import {Observable, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private readonly TEST_USER_URL = 'api/user/users.json';

    private readonly USER_URL = 'users';

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

    getUsers(): Observable<IUser[]> {
        return this._http.get<IUser[]>(environment.production ? this.USER_URL : this.TEST_USER_URL ).pipe(
            tap(data => console.log('All: ' + JSON.stringify(data))),
            catchError( err => UserService.handleError(err))
        );
    }
}
