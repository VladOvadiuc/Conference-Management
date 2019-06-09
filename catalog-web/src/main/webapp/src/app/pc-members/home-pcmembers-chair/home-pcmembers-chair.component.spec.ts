import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePcmembersChairComponent } from './home-pcmembers-chair.component';

describe('HomePcmembersChairComponent', () => {
  let component: HomePcmembersChairComponent;
  let fixture: ComponentFixture<HomePcmembersChairComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomePcmembersChairComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomePcmembersChairComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
