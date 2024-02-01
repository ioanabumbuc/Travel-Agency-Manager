import {Component} from '@angular/core';
import {LoginService} from "../_services/login-service.service";
import {User} from "../_models/User";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  user: User = {
    email: "",
    password: "",
    isLoggedIn: false
  }

  isAdmin: boolean = false;
  validLogin: boolean = true;

  constructor(private loginService: LoginService,
              private router: Router) {
  }

  login() {
    console.log("Attempting login with credentials",this.user.email, this.user.password);

    if(this.isAdmin){
      this.loginService.loginAdmin(this.user.email, this.user.password).subscribe({
        next: (resp) => {
          this.validLogin = true;
          this.user = resp;

          localStorage.setItem("email",this.user.email);
          localStorage.setItem("isAdmin","true");

          this.router.navigate(['']).then();
        },
        error: (err) => {
          this.validLogin = false;
          console.error(err);
        }
      })
    }else{
      this.loginService.loginEmployee(this.user.email, this.user.password).subscribe({
        next: (resp) => {
          this.validLogin = true
          this.user = resp;

          localStorage.setItem("email",this.user.email);
          localStorage.setItem("isAdmin","false");

          this.router.navigate(['']).then();
        },
        error: (err) => {
          this.validLogin = false;
          console.error(err);
        }
      })
    }
  }
}
