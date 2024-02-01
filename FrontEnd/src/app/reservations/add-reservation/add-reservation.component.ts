import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ReservationService} from "../../_services/reservation.service";
import {Router} from "@angular/router";
import {ClientService} from "../../_services/client.service";
import {VacationService} from "../../_services/vacation.service";
import {Client, Vacation} from "../../_models/Data";

@Component({
  selector: 'app-add-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['./add-reservation.component.scss']
})
export class AddReservationComponent {
  error: string = '';
  success: boolean = false;
  clients: Client[] = [];
  vacations: Vacation[] = [];

  reservationForm: FormGroup;

  constructor(private reservationService: ReservationService,
              private clientService: ClientService,
              private vacationService: VacationService,
              private router: Router) {
    this.reservationForm = new FormGroup({
      clientId: new FormControl([null], Validators.required),
      vacationId: new FormControl([null], Validators.required),
      nbOfPeople: new FormControl([null], [Validators.required, Validators.min(1), Validators.max(10)]),
      payOnSpot: new FormControl(false, [Validators.required])
    })

    const email = localStorage.getItem("email");
    if (!email) {
      return;
    }

    this.clientService.getClients(email).subscribe({
      next: (resp) => {
        this.clients = resp;
      }
    })

    this.vacationService.getVacations(email).subscribe({
      next: (resp) => {
        this.vacations = resp;
      }
    })
  }

  saveReservation() {
    const email = localStorage.getItem("email");
    if (!email) {
      return;
    }

    const reservation = {
      vacationId: this.reservationForm.controls['vacationId'].value,
      clientId: this.reservationForm.controls['clientId'].value,
      numberOfPeople: this.reservationForm.controls['nbOfPeople'].value,
      paymentStatus: this.reservationForm.controls['payOnSpot'].value ?? 'PAY_ON_SPOT'
    }

    this.reservationService.addReservation(email, reservation).subscribe({
      next: (resp) => {
        this.success = true;
        this.error = '';
      },
      error: (err) => {
        this.success = false
        this.error = err.error.message
      }
    })
  }

  selectClient(event: any) {
    this.reservationForm.controls['clientId'].setValue(event.target.value);
  }

  selectVacation(event: any) {
    this.reservationForm.controls['vacationId'].setValue(event.target.value);
  }

  back() {
    this.router.navigate(['/reservations']).then();
  }
}
