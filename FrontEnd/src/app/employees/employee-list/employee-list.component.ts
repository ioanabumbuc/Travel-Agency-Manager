import {Component} from '@angular/core';
import {Employee, ResponseString} from "../../_models/Data";
import {EmployeeService} from "../../_services/employee.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent {
  employees: Employee[] = [];
  idToDelete: number = -1;
  email: string = '';

  constructor(private employeeService: EmployeeService,
              private router: Router) {
  }

  ngOnInit() {
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }
    this.email = email;
    this.getEmployees();
  }

  getEmployees() {
    this.employeeService.getEmployees(this.email).subscribe({
      next: (resp) => {
        this.employees = resp;
        console.log("Vacations", this.employees);
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  goToEdit(id: number | undefined) {
    if (!id) {
      return;
    }
    this.router.navigate(['employees/edit', id]).then();
  }

  setDeleteId(index: number | undefined) {
    if (!index) {
      return;
    }
    this.idToDelete = index;
  }

  deleteClient() {
    if (this.idToDelete == -1 || !this.email) {
      return;
    }
    this.employeeService.deleteEmployee(this.idToDelete, this.email).subscribe({
      next: (resp: ResponseString) => {
        this.getEmployees();
      },
      error: (err) => {
        console.error(err);
      }
    })
  }
}
