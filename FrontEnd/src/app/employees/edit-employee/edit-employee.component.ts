import {Component} from '@angular/core';
import {Client, Employee} from "../../_models/Data";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ClientService} from "../../_services/client.service";
import {EmployeeService} from "../../_services/employee.service";

@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrls: ['./edit-employee.component.scss']
})
export class EditEmployeeComponent {
  id: number;
  error: string = '';
  success: boolean = false;
  showForm: boolean = false;

  // @ts-ignore
  employee: Employee;

  // @ts-ignore
  employeeForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private employeeService: EmployeeService) {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }
    this.employeeService.getEmployees(email).subscribe({
      next: (employees) => {
        for (let e of employees) {
          if (e.id === this.id) {
            this.employee = e;
            this.buildForm();
            this.showForm = true;
            break;
          }
        }
      }
    })
  }

  buildForm() {
    this.employeeForm = new FormGroup({
      fullName: new FormControl(this.employee.fullName, Validators.required),
      email: new FormControl(this.employee.email, [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    })
  }

  saveEmployee() {
    const employee = {
      id: this.id,
      fullName: this.employeeForm.controls['fullName'].value,
      email: this.employeeForm.controls['email'].value,
      password: this.employeeForm.controls['password'].value
    }
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }
    this.success = false;
    this.employeeService.updateEmployee(employee, email).subscribe({
      next: (resp) => {
        this.success = true;
        this.error = '';
      },
      error: (err) => {
        this.error = err.error.message;
        this.success = false;
      }
    })
  }

}
