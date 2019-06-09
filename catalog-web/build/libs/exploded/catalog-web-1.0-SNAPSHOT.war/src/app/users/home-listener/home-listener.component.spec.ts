import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeListenerComponent } from './home-listener.component';

describe('HomeListenerComponent', () => {
  let component: HomeListenerComponent;
  let fixture: ComponentFixture<HomeListenerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeListenerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeListenerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
