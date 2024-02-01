import {Component} from '@angular/core';
import {ClientService} from "../../_services/client.service";
import {Client, ResponseString} from "../../_models/Data";
import {Router} from "@angular/router";

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.scss']
})
export class ClientListComponent {
  clients: Client[] = [];
  clientToDelete: number = -1;
  email: string = '';

  constructor(private clientService: ClientService,
              private router:Router) {
  }

  ngOnInit() {
    const email = localStorage.getItem('email');
    if (!email) {
      return;
    }
    this.email = email;
    this.getClients();
  }

  getClients() {
    if (!this.email) {
      return;
    }
    this.clientService.getClients(this.email).subscribe({
      next: (resp) => {
        this.clients = resp;
        console.log("Vacations", this.clients);
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  goToEdit(id:number | undefined){
    if(!id){
      return;
    }
    this.router.navigate(['clients/edit', id]).then();
  }

  setDeleteId(index: number | undefined) {
    if(index){
      this.clientToDelete = index;
    }
  }

  deleteClient() {
    if (this.clientToDelete == -1 || !this.email) {
      return;
    }
    this.clientService.deleteClient(this.clientToDelete, this.email).subscribe({
      next: (resp: ResponseString) => {
        this.getClients();
      },
      error: (err) => {
        console.error(err);
      }
    })
  }
}
