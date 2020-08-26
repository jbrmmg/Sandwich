import { Component } from '@angular/core';
import {ISetting} from './setting';
import {SettingsService} from './settings.service';

@Component({
    templateUrl: './settings.manage.component.html',
    styleUrls: ['./settings.manage.component.css']
})
export class SettingsManageComponent {
    settings: ISetting[];

    constructor(private readonly _settingService: SettingsService) {
        this._settingService.getSettings().subscribe(
            users => {
                this.settings = users;
            },
            error => console.log('Failed to get the users.'),
            () => console.log('Finished get users.')
        );
    }
}
