import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePcmembersComponent } from './home-pcmembers.component';

describe('HomePcmembersComponent', () => {
  let component: HomePcmembersComponent;
  let fixture: ComponentFixture<HomePcmembersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomePcmembersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomePcmembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
