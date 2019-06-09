import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeSpeakerComponent } from './home-speaker.component';

describe('HomeSpeakerComponent', () => {
  let component: HomeSpeakerComponent;
  let fixture: ComponentFixture<HomeSpeakerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeSpeakerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeSpeakerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
