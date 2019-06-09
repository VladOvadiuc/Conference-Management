import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LogInPcComponent } from './log-in-pc.component';

describe('LogInPcComponent', () => {
  let component: LogInPcComponent;
  let fixture: ComponentFixture<LogInPcComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LogInPcComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LogInPcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
