import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client, ResponseString} from "../_models/Data";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  url: string = "http://localhost:8080/clients"

  constructor(private http: HttpClient) {
  }

  public getClients(email: string): Observable<Client[]> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.get<Client[]>(this.url + "/all", {headers: headers});
  }

  public updateClient(client: Client, email: string): Observable<Client> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.put<Client>(this.url + '/update/' + client.id, client,{headers: headers});
  }

  public deleteClient(clientId: number, email: string): Observable<any> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.delete(this.url + '/delete/' + clientId, {headers: headers});
  }

  public addClient(client: Client, email: string): Observable<any> {
    const headers = new HttpHeaders().set('Email', email);
    const request = {
      fullName: client.fullName,
      email: client.email,
      phoneNumber: client.phoneNumber
    }
    return this.http.post<Client>(this.url + '/add', request, {headers: headers});
  }
}
