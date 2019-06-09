import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PcMembersComponent } from './pc-members.component';

describe('PcMembersComponent', () => {
  let component: PcMembersComponent;
  let fixture: ComponentFixture<PcMembersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PcMembersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PcMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
