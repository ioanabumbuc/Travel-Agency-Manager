import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClientService} from "../../_services/client.service";
import {Client} from "../../_models/Data";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.scss']
})
export class AddClientComponent {
  showForm: boolean = true;
  error: string = '';

  clientForm: FormGroup;

  constructor(private clientService: ClientService,
              private router: Router) {
    this.clientForm = new FormGroup({
      fullName: new FormControl([null], Validators.required),
      email: new FormControl([null], [Validators.required, Validators.email]),
      phoneNb: new FormControl([null],
        [
          Validators.required,
          Validators.pattern(/^[0-9]+$/),
          Validators.minLength(5),
          Validators.maxLength(12)
        ])
    });
  }


  saveClient() {
    const client = {
      fullName: this.clientForm.controls['fullName'].value,
      email: this.clientForm.controls['email'].value,
      phoneNumber: this.clientForm.controls['phoneNb'].value,
    }
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }
    this.clientService.addClient(client, email).subscribe({
      next: (client) => {
        console.log('Client added successfully: ', client);
        this.showForm = false;
      },
      error: (err) => {
        this.error = err.error.message;
      }
    })
  }


  back() {
    this.router.navigate(['/clients']).then();
  }
}
