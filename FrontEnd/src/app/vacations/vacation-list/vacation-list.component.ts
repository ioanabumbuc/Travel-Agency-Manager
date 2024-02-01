import {Component} from '@angular/core';
import {VacationService} from "../../_services/vacation.service";
import {ResponseString, Vacation} from "../../_models/Data";
import {Router} from "@angular/router";

@Component({
  selector: 'app-vacation-list',
  templateUrl: './vacation-list.component.html',
  styleUrls: ['./vacation-list.component.scss']
})
export class VacationListComponent {
  vacations: Vacation[] = [];
  idToDelete: number = -1;
  email: string = '';

  constructor(private vacationService: VacationService,
              private router: Router) {
  }

  ngOnInit() {
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }
    this.email = email;
    this.getVacations();
  }

  getVacations() {
    this.vacationService.getVacations(this.email).subscribe({
      next: (resp) => {
        for (let vacation of resp) {
          if (vacation.hotelName) {
            vacation.dtype = 'STAY'
          }
          if (vacation.shipName || vacation.boardingLocation) {
            vacation.dtype = 'CRUISE'
          }
          if (vacation.itinerary || vacation.guide) {
            vacation.dtype = 'TOUR'
          }
        }
        this.vacations = resp;
        console.log("Vacations", this.vacations);
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
    this.router.navigate(['vacations/edit', id]).then();
  }

  setDeleteId(index: number | undefined) {
    if (index) {
      this.idToDelete = index;
    }
  }

  deleteClient() {
    if (this.idToDelete == -1 || !this.email) {
      return;
    }
    this.vacationService.deleteVacation(this.idToDelete, this.email).subscribe({
      next: (resp: ResponseString) => {
        this.getVacations();
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  goToDetails(id: number | undefined) {
    if (!id) {
      return;
    }
    this.router.navigate(['vacations', id]).then();
  }
}
