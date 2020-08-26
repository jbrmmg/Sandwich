import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {ISetting} from './setting';
import {Observable, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {environment} from '../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class SettingsService {
    private readonly TEST_SETTING_URL = 'api/setting/settings.json';

    private readonly SETTING_URL = 'settings';

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

    getSettings(): Observable<ISetting[]> {
        return this._http.get<ISetting[]>(environment.production ? this.SETTING_URL : this.TEST_SETTING_URL ).pipe(
            tap(data => console.log('All: ' + JSON.stringify(data))),
            catchError( err => SettingsService.handleError(err))
        );
    }
}
