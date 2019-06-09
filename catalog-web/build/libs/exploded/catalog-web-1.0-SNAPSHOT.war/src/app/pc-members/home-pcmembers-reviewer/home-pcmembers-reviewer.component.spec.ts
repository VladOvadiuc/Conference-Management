import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePcmembersReviewerComponent } from './home-pcmembers-reviewer.component';

describe('HomePcmembersReviewerComponent', () => {
  let component: HomePcmembersReviewerComponent;
  let fixture: ComponentFixture<HomePcmembersReviewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomePcmembersReviewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomePcmembersReviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
