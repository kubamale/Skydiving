import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SkydiverApprovalModel } from 'src/shared/skydiver';
import { ApprovalService } from '../approval.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-approval-request',
  templateUrl: './approval-request.component.html',
  styleUrls: ['./approval-request.component.css']
})
export class ApprovalRequestComponent implements OnInit {

  constructor(private approvalService: ApprovalService, private builder: FormBuilder){}

  approvalForm!: FormGroup;
  @Input()
  approval!: SkydiverApprovalModel;
  @Output() deleteApprovalEmiter = new EventEmitter<SkydiverApprovalModel>();
  allOptions: boolean = false;
  ngOnInit(): void {
    this.approvalForm = this.builder.group({
      licence:[this.approval.licence, Validators.required],
      role: ['', Validators.required],
      privileges: ['', ]
    })

    let role = window.localStorage.getItem('role') as string;

    if (role === 'ADMIN' || role === 'MANIFEST'){
      this.allOptions = true;
    }

  }

  reject(approval: SkydiverApprovalModel): void {
    this.approvalService.rejectApprovalService(approval.email).subscribe(data => {
      this.deleteApprovalEmiter.emit(data as SkydiverApprovalModel);
    });
  }

  approve(){

    let body = {
      email: this.approval.email,
      role: this.approvalForm.get('role')?.value,
      licence: this.approvalForm.get('licence')?.value,
      privileges: this.approvalForm.get('privileges')?.value
    }
    this.approvalService.approve(body).subscribe(data => {
      this.deleteApprovalEmiter.emit(data as SkydiverApprovalModel);
    });
  }
}
