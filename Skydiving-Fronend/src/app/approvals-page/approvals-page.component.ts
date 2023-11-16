import { Component, OnInit } from '@angular/core';
import { ApprovalService } from '../approval.service';
import { SkydiverApprovalModel } from 'src/shared/skydiver';

@Component({
  selector: 'app-approvals-page',
  templateUrl: './approvals-page.component.html',
  styleUrls: ['./approvals-page.component.css']
})
export class ApprovalsPageComponent implements OnInit {

  approvalRequests: SkydiverApprovalModel[] = []; 

  constructor(private approvalService: ApprovalService){}

  ngOnInit(): void {
    this.approvalService.getMyApprovalReqests().subscribe(data => {
      this.approvalRequests = data as SkydiverApprovalModel[];
      console.log(data);
    })
  }

  reject(approval: SkydiverApprovalModel): void {
    this.approvalService.rejectApprovalService(approval.email).subscribe(data => {
      this.approvalRequests.splice(this.approvalRequests.indexOf(data as SkydiverApprovalModel), 1);
    });
  }

}
