import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {EmployeeService} from "../../_services/employee.service";

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss']
})
export class AddEmployeeComponent {
  error: string = '';
  success: boolean = false;

  employeeForm: FormGroup;

  constructor(private employeeService: EmployeeService) {
    this.employeeForm = new FormGroup({
      fullName: new FormControl([null], Validators.required),
      email: new FormControl([null], [Validators.required, Validators.email]),
      password: new FormControl([null], Validators.required)
    });
  }

  saveEmployee() {
    const employee = {
      fullName: this.employeeForm.controls['fullName'].value,
      email: this.employeeForm.controls['email'].value,
      password: this.employeeForm.controls['password'].value,
    }
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }
    this.employeeService.addEmployee(employee, email).subscribe({
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
