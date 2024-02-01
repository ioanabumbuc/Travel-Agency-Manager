import {Component} from '@angular/core';
import {LoginService} from "../_services/login-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent {
  isAdmin: boolean = false;

  constructor(private loginService: LoginService,
              private router: Router) {
  }

  ngOnInit() {
    this.getLoggedUser();
  }

  getLoggedUser() {
    const email = localStorage.getItem("email");
    if (!email) {
      return;
    }
    this.isAdmin = email === "admin";
  }

  onLogout() {
    const email = localStorage.getItem("email");
    if (!email) {
      return;
    }
    if (email === "admin") {
      this.loginService.logoutAdmin(email);
    } else {
      this.loginService.logoutEmployee(email);
    }
    localStorage.clear();
    this.router.navigate(["/login"]).then();
  }

  goHome() {
    this.router.navigate(['/']).then();
  }

  goTo(destination: string) {
    switch (destination) {
      case 'VACATIONS':
        this.router.navigate(['/vacations']).then();
        break;
      case 'ADD_VACATION':
        this.router.navigate(['/vacations/add']).then();
        break;
      case 'CLIENTS':
        this.router.navigate(['/clients']).then();
        break;
      case 'ADD_CLIENT':
        this.router.navigate(['clients/add']).then();
        break;
      case 'EMPLOYEES':
        this.router.navigate(['/employees']).then();
        break;
      case 'ADD_EMPLOYEE':
        this.router.navigate(['/employees/add']).then();
        break;
      case 'RESERVATIONS':
        this.router.navigate(['/reservations']).then();
        break;
      case 'ADD_RESERVATION':
        this.router.navigate(['/reservations/add']).then();
        break;
    }
  }

}
