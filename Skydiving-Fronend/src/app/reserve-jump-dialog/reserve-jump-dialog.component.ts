import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DepartureCreateModel } from 'src/shared/departure';
import { SkydiverInfoModel } from 'src/shared/skydiver';
import { UserService } from '../user.service';

@Component({
  selector: 'app-reserve-jump-dialog',
  templateUrl: './reserve-jump-dialog.component.html',
  styleUrls: ['./reserve-jump-dialog.component.css']
})
export class ReserveJumpDialogComponent implements OnInit {
  role!: string;
  allowAffBook = ['ADMIN', 'MANIFEST', 'INSTRUCTOR'];
  allowTandemBook = ['ADMIN', 'MANIFEST', 'TANDEM_PILOT'];
  instructors: SkydiverInfoModel[] = [];
  affSkydivers: SkydiverInfoModel[] = [];
  customers: SkydiverInfoModel[] = [];
  skydivers: SkydiverInfoModel[] = [];

  whosJumpDecision!: string;

  reservation!: DepartureCreateModel;

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<ReserveJumpDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DepartureCreateModel,
  ) {
    this.role = window.localStorage.getItem('role') as string;
  }

  ngOnInit(): void {
    
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  jumpTypeSelected(){
    if(this.role !== 'USER'){
      if (this.data.jumpType === 'AFF'){
          this.userService.getSkydivers('instructors').subscribe(data => this.instructors = data as SkydiverInfoModel[]); 
          this.userService.getSkydivers('affSkydivers').subscribe(data => this.affSkydivers = data as SkydiverInfoModel[]); ;
      }
      else if (this.data.jumpType === 'TANDEM'){
        this.userService.getSkydivers('tandempilots').subscribe(data => this.instructors = data as SkydiverInfoModel[]); ;
        this.userService.getSkydivers('customers').subscribe(data => this.customers = data as SkydiverInfoModel[]); ;
      }
      else{
        this.userService.getSkydivers('skydivers').subscribe(data => this.skydivers = data as SkydiverInfoModel[]); ;
      }
    }
    
  }
}

