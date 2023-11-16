import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovalsPageComponent } from './approvals-page.component';

describe('ApprovalsPageComponent', () => {
  let component: ApprovalsPageComponent;
  let fixture: ComponentFixture<ApprovalsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApprovalsPageComponent]
    });
    fixture = TestBed.createComponent(ApprovalsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
