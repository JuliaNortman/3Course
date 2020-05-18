import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Flight } from './models/flight';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  constructor(private http: HttpClient) { }

  getAllFlights(): Observable<Flight[]> {
    return this.http.get<Flight[]>("http://localhost:8080/flight/all");
  }
}
