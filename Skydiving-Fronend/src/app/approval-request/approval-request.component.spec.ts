import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovalRequestComponent } from './approval-request.component';

describe('ApprovalRequestComponent', () => {
  let component: ApprovalRequestComponent;
  let fixture: ComponentFixture<ApprovalRequestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApprovalRequestComponent]
    });
    fixture = TestBed.createComponent(ApprovalRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
