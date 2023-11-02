import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DepartureCreateModel } from 'src/shared/departure';

@Component({
  selector: 'app-reserve-jump-dialog',
  templateUrl: './reserve-jump-dialog.component.html',
  styleUrls: ['./reserve-jump-dialog.component.css']
})
export class ReserveJumpDialogComponent implements OnInit {
  role!: string;
  allowAffBook = ['ADMIN', 'MANIFEST', 'INSTRUCTOR'];
  allowTandemBook = ['ADMIN', 'MANIFEST', 'TANDEM_PILOT'];
  whosJumpDecision!: string;

  constructor(
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
}

