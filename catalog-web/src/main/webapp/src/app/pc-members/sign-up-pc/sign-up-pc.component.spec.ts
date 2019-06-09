import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignUpPcComponent } from './sign-up-pc.component';

describe('SignUpPcComponent', () => {
  let component: SignUpPcComponent;
  let fixture: ComponentFixture<SignUpPcComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SignUpPcComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignUpPcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
