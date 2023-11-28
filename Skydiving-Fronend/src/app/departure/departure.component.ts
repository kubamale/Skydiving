import { Component, Input, Output, OnInit, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { DepartureCreateModel, DepartureDetailsModel, DepartureUpdateModel } from 'src/shared/departure';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PlaneModel } from 'src/shared/plane';
import { PlaneService } from '../plane.service';
import { DepartureService } from '../departure.service';
import { ReserveJumpDialogComponent } from '../reserve-jump-dialog/reserve-jump-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { SkydiverModel } from 'src/shared/skydiver';

@Component({
  selector: 'app-departure',
  templateUrl: './departure.component.html',
  styleUrls: ['./departure.component.css']
})
export class DepartureComponent implements OnInit{
  editColumns: string[] = ['First Name', 'Last Name', 'License', 'Weight', 'Jump Type', 'Delete'];
  nonEditColumns: string[] =['First Name', 'Last Name', 'License', 'Weight', 'Jump Type'];
  usersToDelete = new Set<number>();
  @Output() deleteDepartureEmiter = new EventEmitter<DepartureDetailsModel>();
  @Input()
  departure!: DepartureDetailsModel;
  planes: PlaneModel[] = [];
  showButton: boolean = false;
  showCancelJumpButton: boolean = false;
  email!: string;
  edit: boolean = false;
  rolesAllowedToEdit = ['ADMIN', 'MANIFEST'];
  editable: boolean = false;
  editForm!: FormGroup;
  constructor(private cdr: ChangeDetectorRef, private formBuilder: FormBuilder, private dialog: MatDialog, private planeService: PlaneService, private departureService: DepartureService){
    
  }
  ngOnInit(): void {
    this.planes.push(this.departure.plane);
    this.editForm = this.formBuilder.group({
      time: [this.departure.time, [Validators.required]],
      allowStudents: [this.departure.allowStudents, [Validators.required]],
      allowAFF: [this.departure.allowAFF, [Validators.required]],
      planeId: [this.departure.plane.id, [Validators.required]],
    })

    this.email = window.localStorage.getItem('email') as string;
    this.showCancelJumpButtonCheck();
  }

  private showCancelJumpButtonCheck(){
    for (let sky of this.departure.skydivers){
      console.log(sky.email);
      if(sky.email === this.email){
        this.showCancelJumpButton = true;
        break; 
      }
    }
  }

  showButtons(){
    this.showButton = true;
    if(this.rolesAllowedToEdit.indexOf(window.localStorage.getItem('role') as string) !== -1) {
      this.edit = true;
    }

  }

  hideButtons(){
    this.showButton = false;
    this.edit = false;
  }

  editDeparture(){
    this.editable = true;
    this.showButton = false;
    this.planeService.getAllPlanes().subscribe(data => this.planes = data as PlaneModel[]);
    
  }

  cancelEditing(){
    this.showButton = false;
    this.edit = false;
    this.editable = false;
    this.usersToDelete.clear();
  }

  save(){
    let update = this.editForm.value as DepartureUpdateModel;
    update.date = this.departure.date;
    update.id = this.departure.id;
    let users : number[] = [];
    this.usersToDelete.forEach(user => users.push(user));
    update.usersId = users;

    this.departureService.updateDeparture(update).subscribe(data => {
        this.departure = data as DepartureDetailsModel;
    });
 
    

    this.cancelEditing();
  }

  deleteDeparture(){
    this.departureService.deleteDeparture(this.departure.id).subscribe();
    this.deleteDepartureEmiter.emit(this.departure);
  }

  shouldDelete(dep: any, id: number){
    if(this.usersToDelete.has(id)){
      this.usersToDelete.delete(id);
    }
    else{
      this.usersToDelete.add(id);
    }
  }


  openDialog(): void {
    const dialogRef = this.dialog.open(ReserveJumpDialogComponent, {
      data:{
        skydiverEmail:window.localStorage.getItem('email') as string,
        departureId: this.departure.id,
        jumpType: ''
      }
    });

    dialogRef.afterClosed().subscribe(result => {

      if (result !== undefined) {
        console.log(result);
      this.departureService.bookJump(result as DepartureCreateModel).subscribe(res => { 
      this.departure = res as DepartureDetailsModel;
      console.log(this.departure.skydivers);
      this.showCancelJumpButtonCheck();
      });
    }
    });
  }

  cancelJump(){
    this.departureService.cancelJump(this.email, this.departure.id).subscribe(res => {
      this.departure = res as DepartureDetailsModel;
      this.showCancelJumpButton = false;
    })
  }
}
