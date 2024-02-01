import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {VacationService} from "../../_services/vacation.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-vacation',
  templateUrl: './add-vacation.component.html',
  styleUrls: ['./add-vacation.component.scss']
})
export class AddVacationComponent {
  error: string = '';
  success: boolean = false;
  type: string = 'STAY';

  vacationForm: FormGroup;

  constructor(private vacationService: VacationService,
              private router: Router) {
    this.vacationForm = new FormGroup({
      destination: new FormControl([null], Validators.required),
      startDate: new FormControl([null], [Validators.required, Validators.pattern(/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/)]),
      endDate: new FormControl([null], [Validators.required, Validators.pattern(/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/)]),
      price: new FormControl([null], Validators.required),
      availability: new FormControl([null], Validators.required),
      hotelName: new FormControl([null], Validators.required),
      shipName: new FormControl([null], Validators.required),
      boardingLocation: new FormControl([null], Validators.required),
      guide: new FormControl([null], Validators.required),
      itinerary: new FormControl([null], Validators.required),
    });
  }

  setType(type: string) {
    // this.vacationForm.controls['type'].setValue(type);
    this.type = type;
    if (type == 'STAY') {
      this.vacationForm.controls['hotelName'].addValidators(Validators.required);
      this.vacationForm.controls['shipName'].clearValidators();
      this.vacationForm.controls['boardingLocation'].clearValidators();
      this.vacationForm.controls['guide'].clearValidators();
      this.vacationForm.controls['itinerary'].clearValidators();
    } else if (type == 'CRUISE') {
      this.vacationForm.controls['shipName'].addValidators(Validators.required);
      this.vacationForm.controls['boardingLocation'].addValidators(Validators.required);
      this.vacationForm.controls['hotelName'].clearValidators();
      this.vacationForm.controls['guide'].clearValidators();
      this.vacationForm.controls['itinerary'].clearValidators();
    } else {
      this.vacationForm.controls['guide'].addValidators(Validators.required);
      this.vacationForm.controls['itinerary'].addValidators(Validators.required);
      this.vacationForm.controls['hotelName'].clearValidators();
      this.vacationForm.controls['boardingLocation'].clearValidators();
      this.vacationForm.controls['shipName'].clearValidators();
    }
  }

  saveVacation() {
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }

    let dateParts = this.vacationForm.controls['startDate'].value.split('/');
    const startDate = new Date(Number(dateParts[2]),
      Number(dateParts[1]),
      Number(dateParts[0]));
    dateParts = this.vacationForm.controls['endDate'].value.split('/');
    const endDate = new Date(Number(dateParts[2]),
      Number(dateParts[1]),
      Number(dateParts[0]));

    const vacation = {
      dtype: this.type,
      destination: this.vacationForm.controls['destination'].value,
      startDate: startDate,
      endDate: endDate,
      price: this.vacationForm.controls['price'].value,
      availability: this.vacationForm.controls['availability'].value,
      hotelName: this.vacationForm.controls['hotelName'].value,
      shipName: this.vacationForm.controls['shipName'].value,
      boardingLocation: this.vacationForm.controls['boardingLocation'].value,
      guide: this.vacationForm.controls['guide'].value,
      itinerary: this.vacationForm.controls['itinerary'].value,
    }
    this.vacationService.addVacation(vacation, email).subscribe({
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
