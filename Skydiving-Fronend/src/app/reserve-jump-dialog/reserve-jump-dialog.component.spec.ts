import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReserveJumpDialogComponent } from './reserve-jump-dialog.component';

describe('ReserveJumpDialogComponent', () => {
  let component: ReserveJumpDialogComponent;
  let fixture: ComponentFixture<ReserveJumpDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReserveJumpDialogComponent]
    });
    fixture = TestBed.createComponent(ReserveJumpDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
