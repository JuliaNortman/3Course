import { Component, OnInit } from '@angular/core';
import { Flight } from '../models/flight';
import { City } from '../models/city';
import { CityServiceService } from '../city-service.service';

@Component({
  selector: 'app-add-flight',
  templateUrl: './add-flight.component.html',
  styleUrls: ['./add-flight.component.sass']
})
export class AddFlightComponent implements OnInit {

  flight: Flight = new Flight();
  cities: City[];

  constructor(private cityService: CityServiceService) { }

  ngOnInit() {
    this.getAllCities();
  }

  getAllCities() {
    this.cityService.getAllCities().subscribe(city => this.cities = city);
  }

}
