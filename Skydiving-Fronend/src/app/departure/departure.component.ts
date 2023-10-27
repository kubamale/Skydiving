import { Component, Input } from '@angular/core';
import { DepartureDetailsModel } from 'src/shared/departure';

@Component({
  selector: 'app-departure',
  templateUrl: './departure.component.html',
  styleUrls: ['./departure.component.css']
})
export class DepartureComponent {
  @Input()
  departure!: DepartureDetailsModel;

  showButton: boolean = false;
  edit: boolean = false;
  rolesAllowedToEdit = ['ADMIN', 'MANIFEST'];
  editable: boolean = false;


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
  }

  cancelEditing(){
    this.showButton = false;
    this.edit = false;
    this.editable = false;
  }

}
