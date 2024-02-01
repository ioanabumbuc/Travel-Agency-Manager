import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client, Vacation} from "../_models/Data";

@Injectable({
  providedIn: 'root'
})
export class VacationService {

  url: string = "http://localhost:8080/vacations"

  constructor(private http: HttpClient) {
  }

  public getVacations(email: string): Observable<Vacation[]> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.get<Vacation[]>(this.url + "/all", {headers: headers});
  }

  public getVacationById(email: string, id: number): Observable<Vacation> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.get<Vacation>(this.url + "/" + id, {headers: headers});
  }

  public updateVacation(vacation: Vacation, email: string): Observable<Vacation> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.put<Vacation>(this.url + '/update/' + vacation.id, vacation, {headers: headers});
  }

  public deleteVacation(id: number, email: string): Observable<any> {
    const headers = new HttpHeaders().set('Email', email);
    return this.http.delete(this.url + '/delete/' + id, {headers: headers});
  }

  public addVacation(vacation: Vacation, email: string): Observable<any> {
    const headers = new HttpHeaders().set('Email', email);
    let request = {
      dtype: vacation.dtype,
      destination: vacation.destination,
      startDate: vacation.startDate,
      endDate: vacation.endDate,
      price: vacation.price,
      availability: vacation.availability,
      boardingLocation: '',
      shipName: '',
      guide: '',
      itinerary: '',
      hotelName: '',
      attractions: []
    }
    if (vacation.dtype === 'CRUISE' && vacation.boardingLocation && vacation.shipName) {
      request.boardingLocation = vacation.boardingLocation
      request.shipName = vacation.shipName
    }
    if (vacation.dtype === 'TOUR' && vacation.guide && vacation.itinerary) {
      request.guide = vacation.guide;
      request.itinerary = vacation.itinerary
    }
    if (vacation.dtype === 'STAY' && vacation.hotelName) {
      request.hotelName = vacation.hotelName
    }
    return this.http.post<Vacation>(this.url + '/add', request, {headers: headers});
  }
}
