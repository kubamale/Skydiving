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
    })
  }

  deleteApproval(approval: SkydiverApprovalModel): void {
    let tmp: SkydiverApprovalModel[] = [];

    for(let app of this.approvalRequests){
      if(app.email !== approval.email){
        tmp.push(app);
      }
      console.log(app.email);
    }

    console.log('deleting');

    this.approvalRequests = tmp;

  }
  

}
