import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LogInUserComponent } from './log-in-user.component';

describe('LogInUserComponent', () => {
  let component: LogInUserComponent;
  let fixture: ComponentFixture<LogInUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LogInUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LogInUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
