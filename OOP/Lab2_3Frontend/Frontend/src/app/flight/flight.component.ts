import { Component, OnInit } from '@angular/core';
import { Flight } from '../models/flight';
import { FlightService } from '../flight.service';

@Component({
  selector: 'app-flight',
  templateUrl: './flight.component.html',
  styleUrls: ['./flight.component.sass']
})
export class FlightComponent implements OnInit {

  flights: Flight[];

  constructor(private flightService: FlightService) { }

  ngOnInit() {
    this.getAllFlights();
  }

  getAllFlights() {
    this.flightService.getAllFlights().subscribe(flight => this.flights = flight);
  }
}
