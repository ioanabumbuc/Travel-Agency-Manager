import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ClientService} from "../../_services/client.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Client} from "../../_models/Data";

@Component({
  selector: 'app-edit-client',
  templateUrl: './edit-client.component.html',
  styleUrls: ['./edit-client.component.scss']
})
export class EditClientComponent {
  id: number;
  error: string = '';
  showForm: boolean = false;
  showSuccess: boolean = false;

  // @ts-ignore
  client: Client;

  // @ts-ignore
  clientForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private clientService: ClientService) {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }

    this.clientService.getClients(email).subscribe({
      next: (clients) => {
        for (let client of clients) {
          if (this.id === client.id) {
            this.client = client;
            this.buildForm();
            this.showForm = true;
            break;
          }
        }
      }
    })
  }

  buildForm() {
    this.clientForm = new FormGroup({
      fullName: new FormControl(this.client.fullName, Validators.required),
      email: new FormControl(this.client.email, [Validators.required, Validators.email]),
      phoneNb: new FormControl(this.client.phoneNumber,
        [
          Validators.required,
          Validators.pattern(/^[0-9]+$/),
          Validators.minLength(5),
          Validators.maxLength(12)
        ])
    })
  }

  back() {
    this.router.navigate(['/clients']).then();
  }

  saveClient() {
    const client = {
      id: this.id,
      fullName: this.clientForm.controls['fullName'].value,
      email: this.clientForm.controls['email'].value,
      phoneNumber: this.clientForm.controls['phoneNb'].value,
    }
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }
    this.showSuccess = false;
    this.clientService.updateClient(client, email).subscribe({
      next: (client) => {
        console.log('Client updated successfully: ', client);
        this.showSuccess = true;
        this.error = '';
      },
      error: (err) => {
        this.error = err.error.message;
        this.showSuccess = false;
      }
    })
  }
}
