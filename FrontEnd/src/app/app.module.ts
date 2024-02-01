import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { MenuComponent } from './menu/menu.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { VacationListComponent } from './vacations/vacation-list/vacation-list.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import { EmployeeListComponent } from './employees/employee-list/employee-list.component';
import { AddClientComponent } from './clients/add-client/add-client.component';
import { EditClientComponent } from './clients/edit-client/edit-client.component';
import { AddVacationComponent } from './vacations/add-vacation/add-vacation.component';
import { EditVacationComponent } from './vacations/edit-vacation/edit-vacation.component';
import { VacationDetailsComponent } from './vacations/vacation-details/vacation-details.component';
import { AddEmployeeComponent } from './employees/add-employee/add-employee.component';
import { EditEmployeeComponent } from './employees/edit-employee/edit-employee.component';
import { ReservationListComponent } from './reservations/reservation-list/reservation-list.component';
import { AddReservationComponent } from './reservations/add-reservation/add-reservation.component';
import { EditReservationComponent } from './reservations/edit-reservation/edit-reservation.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    MenuComponent,
    VacationListComponent,
    ClientListComponent,
    EmployeeListComponent,
    AddClientComponent,
    EditClientComponent,
    AddVacationComponent,
    EditVacationComponent,
    VacationDetailsComponent,
    AddEmployeeComponent,
    EditEmployeeComponent,
    ReservationListComponent,
    AddReservationComponent,
    EditReservationComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
