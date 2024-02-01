import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {AuthGuard} from "./_services/auth-guard";
import {VacationListComponent} from "./vacations/vacation-list/vacation-list.component";
import {ClientListComponent} from "./clients/client-list/client-list.component";
import {EmployeeListComponent} from "./employees/employee-list/employee-list.component";
import {AddClientComponent} from "./clients/add-client/add-client.component";
import {EditClientComponent} from "./clients/edit-client/edit-client.component";
import {AddVacationComponent} from "./vacations/add-vacation/add-vacation.component";
import {EditVacationComponent} from "./vacations/edit-vacation/edit-vacation.component";
import {VacationDetailsComponent} from "./vacations/vacation-details/vacation-details.component";
import {AddEmployeeComponent} from "./employees/add-employee/add-employee.component";
import {EditEmployeeComponent} from "./employees/edit-employee/edit-employee.component";
import {ReservationListComponent} from "./reservations/reservation-list/reservation-list.component";
import {AddReservationComponent} from "./reservations/add-reservation/add-reservation.component";
import {EditReservationComponent} from "./reservations/edit-reservation/edit-reservation.component";

const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'vacations', component: VacationListComponent},
  {path: 'vacations/add', component: AddVacationComponent},
  {path: 'vacations/edit/:id', component: EditVacationComponent},
  {path: 'vacations/:id', component: VacationDetailsComponent},
  {path: 'clients', component: ClientListComponent},
  {path: 'clients/add', component: AddClientComponent},
  {path: 'clients/edit/:id', component: EditClientComponent},
  {path: 'employees', component: EmployeeListComponent},
  {path: 'employees/add', component: AddEmployeeComponent},
  {path: 'employees/edit/:id', component: EditEmployeeComponent},
  {path: 'reservations', component: ReservationListComponent},
  {path: 'reservations/add', component: AddReservationComponent},
  {path: 'reservations/edit/:id', component: EditReservationComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
