import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DepartureService } from '../departure.service';
import { DepartureDetailsModel } from 'src/shared/departure';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PlaneModel } from 'src/shared/plane';
import { PlaneService } from '../plane.service';

@Component({
  selector: 'app-departure-page',
  templateUrl: './departure-page.component.html',
  styleUrls: ['./departure-page.component.css']
})
export class DeparturePageComponent implements OnInit {
  page: number = 0;
  createForm!: FormGroup;
  planes: PlaneModel[] = [];
  date!: string;
  createDeparture: boolean = false;
  departures: DepartureDetailsModel[] = [];
  canCreate: boolean = false;
  rolesAllowedToCreate = ['ADMIN', 'MANIFEST'];

  constructor(private route: ActivatedRoute, private departureService: DepartureService,  private formBuilder: FormBuilder, private planeService: PlaneService){
    this.route.queryParams.subscribe(params => {this.date = params['date'] as string; console.log(params['date'] as string);});
    this.resetForm();
  }
  ngOnInit(): void {
    this.loadDepartures();
    if(this.rolesAllowedToCreate.indexOf(window.localStorage.getItem('role') as string) !== -1) {
      this.canCreate = true;
    }
  }

  deleteDeparture(departure: DepartureDetailsModel){
    this.departures = this.departures.filter(data => data.id != departure.id);
  }

  showForm(){
    this.planeService.getAllPlanes().subscribe(plane => this.planes = plane as PlaneModel[]);
    this.createDeparture = true;
  }

  cancel(){
    this.resetForm();
    this.createDeparture = false;
  }

  submit(){
    console.log('submit');
    console.log(this.createForm);
    if(this.createForm.valid){
      console.log('validating');
      this.departureService.createDeparture(this.createForm.value).subscribe(dep => {
        this.departures.push(dep as DepartureDetailsModel);
        this.departures.sort((a, b) => {
          const timeA = Number(a.time.replace(":", ""));
          const timeB = Number(b.time.replace(":", ""));
          return timeA - timeB;
        });
        this.cancel();
       });

    }

    
  }

  loadDepartures(){
    this.departureService.getDeparturesDetails(this.date, this.page).subscribe(data => {
      this.departures = this.departures.concat(data as DepartureDetailsModel[]).sort((a, b) => {
        const timeA = Number(a.time.replace(":", ""));
        const timeB = Number(b.time.replace(":", ""));
        return timeA - timeB;
      });
      this.page++;
    });
  }

  private resetForm(){
     this.createForm = this.formBuilder.group({
      date: [this.date, [Validators.required]],
      time: ['', [Validators.required]],
      allowStudents: ['', ],
      allowAFF: ['', ],
      planeId: ['', [Validators.required]]
    })
  }


}
