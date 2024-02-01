import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Reservation, ReservationStripped, Vacation} from "../_models/Data";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  url: string = "http://localhost:8080/reservations"

  constructor(private http: HttpClient) {
  }

  public getReservations(email: string): Observable<Reservation[]> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.get<Reservation[]>(this.url + "/all", {headers: headers});
  }

  public addReservation(email: string, reservation: ReservationStripped): Observable<Reservation> {
    const headers = new HttpHeaders().set('Email', email);
    const request = {
      vacationId: reservation.vacationId,
      clientId: reservation.clientId,
      numberOfPeople: reservation.numberOfPeople,
      paymentStatus: reservation.paymentStatus
    }
    return this.http.post<Reservation>(this.url + '/add', request, {headers: headers});
  }

  public payReservation(email: string, id: number): Observable<Reservation> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.put<Reservation>(this.url + '/pay/' + id, {}, {headers: headers});
  }

  public cancelReservation(email: string, id: number): Observable<any> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.delete(this.url + '/cancel/' + id, {headers: headers});
  }
}
