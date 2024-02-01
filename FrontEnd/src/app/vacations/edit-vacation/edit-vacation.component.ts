import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {VacationService} from "../../_services/vacation.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Vacation} from "../../_models/Data";

@Component({
  selector: 'app-edit-vacation',
  templateUrl: './edit-vacation.component.html',
  styleUrls: ['./edit-vacation.component.scss']
})
export class EditVacationComponent {
  id: number;
  error: string = '';
  success: boolean = false;
  type: string = '';
  showForm: boolean = false;

  // @ts-ignore
  vacationForm: FormGroup;

  // @ts-ignore
  vacation: Vacation;

  constructor(private vacationService: VacationService,
              private route: ActivatedRoute,
              private router: Router) {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    const email = localStorage.getItem('email');

    if (!email) {
      return;
    }
    this.vacationService.getVacations(email).subscribe({
      next: (vacations) => {
        for (let v of vacations) {
          if (this.id === v.id) {
            if (v.hotelName) {
              v.dtype = 'STAY';
              this.type = 'STAY';
            } else if (v.boardingLocation || v.shipName) {
              v.dtype = 'CRUISE';
              this.type = 'CRUISE';
            } else {
              v.dtype = 'TOUR';
              this.type = 'TOUR';
            }
            this.vacation = v;
            this.buildForm();
            this.showForm = true;
            break;
          }
        }
      }
    })

  }

  buildForm() {
    this.vacationForm = new FormGroup({
      destination: new FormControl(this.vacation.destination, Validators.required),
      startDate: new FormControl(this.vacation.startDate.toString(), [Validators.required]),
      endDate: new FormControl(this.vacation.endDate.toString(), [Validators.required]),
      price: new FormControl(this.vacation.price, Validators.required),
      availability: new FormControl(this.vacation.availability, Validators.required),
    });
    if (this.type === 'STAY') {
      this.vacationForm.addControl('hotelName',
        new FormControl(this.vacation.hotelName, Validators.required));
    } else if (this.type === 'CRUISE') {
      this.vacationForm.addControl('shipName',
        new FormControl(this.vacation.shipName, Validators.required));
      this.vacationForm.addControl('boardingLocation',
        new FormControl(this.vacation.boardingLocation, Validators.required));
    } else {
      this.vacationForm.addControl('guide',
        new FormControl(this.vacation.guide, Validators.required));
      this.vacationForm.addControl('itinerary',
        new FormControl(this.vacation.itinerary, Validators.required));
    }
  }

  saveVacation() {
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }

    let dateParts = this.vacationForm.controls['startDate'].value.split('-');
    const startDate = new Date(Number(dateParts[0]),
      Number(dateParts[1]),
      Number(dateParts[2]));
    dateParts = this.vacationForm.controls['endDate'].value.split('-');
    const endDate = new Date(Number(dateParts[0]),
      Number(dateParts[1]),
      Number(dateParts[2]));

    const vacation = {
      id: this.id,
      dtype: this.type,
      destination: this.vacationForm.controls['destination'].value,
      startDate: startDate,
      endDate: endDate,
      price: this.vacationForm.controls['price'].value,
      availability: this.vacationForm.controls['availability'].value,
      hotelName: this.vacationForm.controls['hotelName'] ?
        this.vacationForm.controls['hotelName'].value : '',
      shipName: this.vacationForm.controls['shipName'] ?
        this.vacationForm.controls['shipName'].value : '',
      boardingLocation: this.vacationForm.controls['boardingLocation'] ?
        this.vacationForm.controls['boardingLocation'].value : '',
      guide: this.vacationForm.controls['guide'] ?
        this.vacationForm.controls['guide'].value : '',
      itinerary: this.vacationForm.controls['itinerary'] ?
        this.vacationForm.controls['itinerary'].value : '',
      attractions: []
    }
    this.vacationService.updateVacation(vacation, email).subscribe({
      next: (resp) => {
        this.success = true;
        this.error = '';
      },
      error: (err) => {
        this.error = err.error.message;
        this.success = false;
      }
    });
  }

  back(){
    this.router.navigate(['/vacations']).then();
  }
}
