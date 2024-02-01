import {Component} from '@angular/core';
import {Reservation, Vacation} from "../../_models/Data";
import {VacationService} from "../../_services/vacation.service";
import {Router} from "@angular/router";
import {ReservationService} from "../../_services/reservation.service";
import {__assign} from "tslib";

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent {
  reservations: Reservation[] = [];
  idToDelete: number = -1;
  idToPay: number = -1;
  email: string = '';

  constructor(private reservationService: ReservationService,
              private router: Router) {
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }
    this.email = email;
    this.getReservations();
  }

  getReservations() {
    this.reservationService.getReservations(this.email).subscribe({
      next: (resp) => {
        this.reservations = resp;
        console.log(resp);
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  setDeleteId(id: number | undefined) {
    if (!id) return;
    this.idToDelete = id;
  }

  setPayId(id: number | undefined) {
    if (!id) return;
    this.idToPay = id;
  }

  deleteReservation() {
    this.reservationService.cancelReservation(this.email, this.idToDelete).subscribe({
      next: (resp) => {
        this.getReservations()
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  pay() {
    this.reservationService.payReservation(this.email, this.idToPay).subscribe({
      next: (resp) => {
        this.getReservations();
      },
      error: (err) => {
        console.error(err);
      }
    })
  }
}
