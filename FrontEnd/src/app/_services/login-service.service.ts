import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {User} from "../_models/User";

@Injectable({
  providedIn: 'root'
})
export class LoginService{
  url: string = "http://localhost:8080/"

  constructor(private http: HttpClient) {
  }

  public loginEmployee(email: String, password: String): Observable<User> {
    const requestBody = {
      email: email,
      password: password
    }
    return this.http.post<User>(this.url + "employees/login", requestBody);
  }

  public logoutEmployee(email: String): Observable<any> {
    const requestBody = {
      email: email,
    }
    return this.http.post(this.url + "employees/logout", requestBody);
  }

  public loginAdmin(email: String, password: String): Observable<User> {
    const requestBody = {
      email: email,
      password: password
    }
    return this.http.post<User>(this.url + "admin/login", requestBody);
  }

  public logoutAdmin(email: String): Observable<any> {
    const requestBody = {
      email: email,
    }
    return this.http.post(this.url + "admin/logout", requestBody);
  }

}
