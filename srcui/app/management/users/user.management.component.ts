import { Component } from '@angular/core';
import {UserService} from "./user.service";
import {IUser} from './user';

@Component({
    templateUrl: './user.management.component.html',
    styleUrls: ['./user.management.component.css']
})
export class UserManagementComponent {
    users: IUser[];

    constructor (private readonly _userService: UserService) {
        this._userService.getUsers().subscribe(
            users => {
                this.users = users;
            },
            error => console.log('Failed to get the users.'),
            () => console.log('Finished get users.')
        );
    }
}
