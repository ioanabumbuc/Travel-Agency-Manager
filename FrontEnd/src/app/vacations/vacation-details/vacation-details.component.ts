import {Component} from '@angular/core';
import {Vacation} from "../../_models/Data";
import {VacationService} from "../../_services/vacation.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-vacation-details',
  templateUrl: './vacation-details.component.html',
  styleUrls: ['./vacation-details.component.scss']
})
export class VacationDetailsComponent {
  // @ts-ignore
  vacation: Vacation;
  id: number;
  type: string = '';

  constructor(private vacationService: VacationService,
              private router: Router,
              private route: ActivatedRoute) {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }

    this.vacationService.getVacationById(email, this.id).subscribe({
      next: (v) => {
        this.vacation = v;
        if (v.hotelName) {
          this.type = 'STAY'
        } else if (v.shipName || v.boardingLocation) {
          this.type = 'CRUISE'
        } else {
          this.type = 'TOUR'
        }
        console.log(this.vacation);
      },
      error: (err) => {
        console.error(err);
      }
    })

  }

  back() {
    this.router.navigate(['vacations']).then();
  }
}
