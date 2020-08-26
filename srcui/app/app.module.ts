import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ModalModule} from 'ngx-bootstrap/modal';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { DatePipe } from '@angular/common';
import { WelcomeComponent } from './home/welcome.component';
import { SelectComponent } from './select/select.component';
import { ManagementComponent } from './management/management.component';
import { IngredientManageComponent } from './management/ingredient/ingredient.manage.component';
import { IngredientTypeManageComponent } from './management/ingredient/ingredient.type.manage.component'
import { SettingsManageComponent } from './management/settings/settings.manage.component'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserManagementComponent } from './management/users/user.management.component';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    SelectComponent,
    ManagementComponent,
    IngredientManageComponent,
    IngredientTypeManageComponent,
    SettingsManageComponent,
    UserManagementComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    BsDatepickerModule.forRoot(),
    ButtonsModule.forRoot(),
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    [BsDropdownModule.forRoot()],
    RouterModule.forRoot([
      {path: 'welcome', component: WelcomeComponent},
      {path: 'select/:id', component: SelectComponent},
      {path: 'management', component: ManagementComponent, children : [
          {path: 'ingredienttype', component: IngredientTypeManageComponent},
          {path: 'ingredient', component: IngredientManageComponent},
          {path: 'settings', component: SettingsManageComponent},
          {path: 'users', component: UserManagementComponent},
        ] },
      {path: '', redirectTo: 'welcome', pathMatch: 'full'},
      {path: '**', redirectTo: 'welcome', pathMatch: 'full'}
    ]),
    CollapseModule
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
