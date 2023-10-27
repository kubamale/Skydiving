import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DepartureService } from '../departure.service';
import { DepartureDetailsModel } from 'src/shared/departure';

@Component({
  selector: 'app-departure-page',
  templateUrl: './departure-page.component.html',
  styleUrls: ['./departure-page.component.css']
})
export class DeparturePageComponent implements OnInit {
  date!: string;
  departures: DepartureDetailsModel[] = [];

  constructor(private route: ActivatedRoute, private departureService: DepartureService){
    this.route.queryParams.subscribe(params => {this.date = params['date'] as string; console.log(params['date'] as string);});
  }
  ngOnInit(): void {
    this.departureService.getDeparturesDetails(this.date).subscribe(data => {
      this.departures = data as DepartureDetailsModel[];
      console.log(data);
    });
  }




}
