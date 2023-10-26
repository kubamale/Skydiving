import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CallendarComponent } from './callendar.component';

describe('CallendarComponent', () => {
  let component: CallendarComponent;
  let fixture: ComponentFixture<CallendarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CallendarComponent]
    });
    fixture = TestBed.createComponent(CallendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
