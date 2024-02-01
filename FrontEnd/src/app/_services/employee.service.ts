import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client, Employee, Vacation} from "../_models/Data";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  url: string = "http://localhost:8080/employees"

  constructor(private http: HttpClient) {
  }

  public getEmployees(email: string): Observable<Employee[]> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.get<Employee[]>(this.url + "/all", {headers: headers});
  }

  public updateEmployee(employee: Employee, email: string): Observable<Employee> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.put<Employee>(this.url + '/update/' + employee.id, employee, {headers: headers});
  }

  public deleteEmployee(employeeId: number, email: string): Observable<any> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.delete(this.url + '/delete/' + employeeId, {headers: headers});
  }

  public addEmployee(employee: Employee, email: string): Observable<any> {
    const headers = new HttpHeaders().set('Email', email);
    const request = {
      fullName: employee.fullName,
      password: employee.password,
      email: employee.email
    }
    return this.http.post<Client>(this.url + '/add', request, {headers: headers});
  }


  generateRandomPassword(length: number) {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let result = ' ';
    const charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
  }

}
