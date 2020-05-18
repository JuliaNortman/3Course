import { Injectable } from '@angular/core';
import { City } from './models/city';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CityServiceService {

  constructor(private http: HttpClient) { }

  getAllCities(): Observable<City[]> {
    return this.http.get<City[]>("http://localhost:8080/city/all");
  }
}
